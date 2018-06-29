var me = {};
me.avatar = "https://bootdey.com/img/Content/avatar/avatar1.png";
me.userId = $("#me").text();

var you = {};
you.avatar = "https://bootdey.com/img/Content/avatar/avatar2.png";
you.userId = $("#you").text();

//--format time to 10:19 PM
function formatAMPM(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0'+minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    return strTime;
}

//-- No use time. It is a javaScript effect.
function insertChat(who, text, time){
    if (time === undefined){    //”==”与”===”是不同的,一个是判断值是否相等,一个是判断值及类型是否完全相等。
        time = 0;
    }
    var control = "";
    var date = formatAMPM(new Date());

    if (who == "you"){
        control ='<li class="left">' +
            '<img  class="avatar" src="' + you.avatar + '" alt="avatar" >' +
            '<div class="body">' +
            '<div class="badge message">' +text +'</div>' +
            '</div></li>';

    }else{
        control ='<li class="right">' +
            '<img  class="avatar" src="' + me.avatar + '" alt="avatar" >' +
            '<div class="body">' +
            '<div class="badge message">' +text +'</div>' +
            '</div></li>';
    }
    //延时
    setTimeout(
        function(){
            $("ul").append(control).scrollTop($("ul").prop('scrollHeight'));    //scrollTop设置滚动的位置，prop方法设置或返回被选元素的属性和值
        }, time);

}

//--clean <ul>
function resetChat(){
    $("ul").empty();
}

//-- Clear Chat
resetChat();

//-- Print Messages


//-- NOTE: No use time on insertChat.

var stompClient = null;

function connect() {
    console.log("connecting");
    var socket = new SockJS('/webSocket-endPoint');  //连接端点endpoint
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        //该方法是接收广播消息
        stompClient.subscribe('/topic/user', function (greeting) {
            insertChat("you", JSON.parse(greeting.body).content);    //回应的内容在content
        });

        stompClient.subscribe('/user/' + you.userId +me.userId+ '/message',function(greeting){    //接受对方的消息
            insertChat("you", JSON.parse(greeting.body).content);
        });

    });
}
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendMessage(message) {
    //发送json字符串到/app/userId/message
    var str = {'content': message/*, 'site':'http://www.runoob.com' */};
    stompClient.send("/app/"+ me.userId +you.userId+"/message", {}, JSON.stringify(str, null, 4));    //以我为联系对象的都会收到，等于群发
}

$(function () {

    $(".Id").hide();

    //初始化连接
    //不加function，则动态按钮失灵
    $(window).bind('onload', connect());
    //网页直接加载连接，首次连接会不成功，js加载不全

    //insertChat("you", "Hello Tom...", 0);
    //insertChat("me", "Hi, Pablo", 1500);


    var len =sessionStorage.length;
    console.log("storage:"+len);
    for(var i = 0; i< len; i++) {
        var key = sessionStorage.key(i);
        var sessionObj = sessionStorage.getItem(key);
        insertChat("you", sessionObj);
        console.log("message:"+sessionObj);
    }
    sessionStorage.clear();

    $('#send').click(function(){
        var message = $("#btn-input").val();
        if(message!='') {
            insertChat("me",message);
            console.log("id: "+you.userId);
            sendMessage(message);
        }
        console.log(message);

        $("#btn-input").val("");

    });

    //断开连接
    $(window).bind('beforeunload', disconnect());
    sessionStorage.clear();

});
