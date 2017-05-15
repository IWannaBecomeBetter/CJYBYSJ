/**
 * Created by Administrator on 2017/4/6.
 */
var code = {
    initClearCanvas:function () {
        var canvas = document.getElementById("verifyCanvas");
        canvas.width = canvas.width;
        canvas.height = canvas.height;
    },
    initCanvas: function () {
        var nums = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "0"];

        var rand1 = nums[Math.floor(Math.random() * nums.length)];
        var rand2 = nums[Math.floor(Math.random() * nums.length)];
        var rand3 = nums[Math.floor(Math.random() * nums.length)];
        var rand4 = nums[Math.floor(Math.random() * nums.length)];
        var code=rand1+rand2+rand3+rand4;
        $('#realCode').val(code);

        var canvas = document.getElementById("verifyCanvas");
        var context = canvas.getContext("2d");

        // Fill the background
        context.fillStyle = "#FFFFFF";
        context.fillRect(0, 0, canvas.width, canvas.height);

        var i = 0;
        // Draw some random lines
        for (i = 0; i < 4; i++) {
            drawline(canvas, context);
        }

        // Sprinkle in some random dots
        for (i = 0; i < 30; i++) {
            drawDot(canvas, context);
        }

        // Draw the pass-phrase string
        context.fillStyle = "#FF0000";
        context.font = "100px Arial";
        context.fillText(rand1, 10, 100);
        context.fillText(rand2, 85, 100);
        context.fillText(rand3, 160, 100);
        context.fillText(rand4, 235, 100);
    }
}


function drawline(canvas, context) {
    context.moveTo(0, Math.floor(Math.random() * canvas.height));
    context.lineTo(canvas.width, Math.floor(Math.random() * canvas.height));
    context.lineWidth = 2.0;
    context.strokeStyle = 'rgb(50,50,50)';
    context.stroke();
}

function drawDot(canvas, context) {
    var px = Math.floor(Math.random() * canvas.width);
    var py = Math.floor(Math.random() * canvas.height);
    context.moveTo(px, py);
    context.lineTo(px+1, py+1);
    context.lineWidth = 5.0;
    context.stroke();
}

function reloadCode(){
    code.initClearCanvas();
    code.initCanvas();
}

$(function () {
    code.initCanvas();
    register.init()
})