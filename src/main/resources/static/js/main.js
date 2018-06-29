var stompClient = null;
var number=0;
var me = {};
me.userId = $("#me").text();

function connect() {
    console.log("connecting");
    var socket = new SockJS('/webSocket-endPoint');  //连接端点endpoint
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        //该方法是接收广播消息
        stompClient.subscribe('/topic/user', function (greeting) {
            window.sessionStorage.setItem(number.toString(), JSON.parse(greeting.body).content);
            number++;
            //insertChat("you", JSON.parse(greeting.body).content);    //回应的内容在content
        });
        stompClient.subscribe('/user/' + me.userId+ '/greet',function(greeting){    //接受对方的消息
            window.sessionStorage.setItem(number.toString(), JSON.parse(greeting.body).content);
            number++;
            $('#notification').show();
            //insertChat("you", JSON.parse(greeting.body).content);
        });
    });
}
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
    sessionStorage.clear();
}

$(document).ready(function () {
    $(window).bind('onload', connect());
    $('#notification').hide();
    setTimeout(function(){stompClient.send("/app/"+me.userId+"/greet");},1500);

    $(window).bind('onunload', disconnect());
});
