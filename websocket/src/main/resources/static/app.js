// stompClient 초기화
// stopClient 객체를 생성하고 ws://localhost:8080/gs-guide-websocket 경롤르 통해 웹 소켓 서버에 연결한다.
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket' // 초기화 , 이 경로는 웹 소켓 서버가 연결을 기다리는 위치 / 연결에 성공하면 클라이언트는 /topic/greeting 목적지를 구독한다.
});

// 웹 소켓 연결에 성공했을 때 호출되는 콜백 함수 정의
stompClient.onConnect = (frame) => {
    const roomId = document.getElementById('roomId').value
    console.log(roomId)

    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings/'+roomId, (greeting) => { // /topic/greetings를 구독하고, 서버가 해당 목적지로 메세지를 보내면 showGreeting() 함수를 호출한다.
        showGreeting(JSON.parse(greeting.body).content); // 메세지를 JSON으로 파싱하고 DOM에 추가한다.
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

// 연결상태에 따라 HTML 요소를 업데이트하는 함수 정의
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

// stomClient를 활성화하여 웹소켓 연결을 시도
function connect() {
    stompClient.activate();
}

// stompClient를 비활성화하여 웹소켓 연결을 종료
function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

// 사용자가 입력한 이름을 가져와서 stompClient를 사용해서 "/app/hello"로 메세지를 보낸다. 이 때 GreetingController.greeting() 메소드가 호출된다.
function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});