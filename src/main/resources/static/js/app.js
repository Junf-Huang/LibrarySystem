var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected); //设置属性和值, input disabled=false,失效
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#userinfo").html("");    //消息记录归零
}

function connect() {
    var socket = new SockJS('/webSocket-endPoint');  //连接端点endpoint
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        //该方法是接收广播消息
        stompClient.subscribe('/topic/user', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);    //回应的内容在content
        });

        stompClient.subscribe('/user/' + 1 + '/message',function(greeting){
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    //发送json字符串到/app/user
    var str = {'name': $("#name").val()/*, 'site':'http://www.runoob.com' */};
    stompClient.send("/app/user", {}, JSON.stringify(str, null, 4));    //使用四个空格缩进
    console.log();
}

/*function sendName() {
    //发送json字符串到/app/user
    var str = {'content': $("#name").val()/!*, 'site':'http://www.runoob.com' *!/};
    stompClient.send("/app/1/message", {}, JSON.stringify(str, null, 4));    //使用四个空格缩进
    console.log();
}*/

function showGreeting(message) {
    $("#userinfo").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});