console.log("test");
$(document).ready(function() {
    console.log("test");
    $.ajax({
        url: "http://localhost:8081/"
    }).then(function(data) {
        console.log(JSON.stringify(data));
    });
});
