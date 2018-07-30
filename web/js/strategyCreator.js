// runs when page has been fully loaded
$(document).ready(function() {

    // runs when the strategy drop down changes value
    $("#strategySelector").change(function() {

        // pull the selected strategy
        let strategy = $("#strategySelector :selected").val();

        // populate selection menu
        if (strategy =='twoMovingAverage') {
            let selectionMenu = '<label for="longAverage">Long Average:</label>' +
                '<input type="number" class="form-control" id="longAverage" placeholder="Ex: 3" required>' +
                '<select class="form-control">' +
                '<option>hours</option>' +
                '<option>minutes</option>' +
                '<option>seconds</option>' +
                '</select>' +
                '<label for="shortAverage">Short Average:</label>' +
                '<input type="number" class="form-control" id="shortAverage" placeholder="Ex: 30" required>' +
                '<select class="form-control">' +
                '<option>hours</option>' +
                '<option selected>minutes</option>' +
                '<option>seconds</option>' +
                '</select>' +
                '<label for="plp">P/L%:</label>' +
                '<input type="number" class="form-control" id="plp" placeholder="Ex: 1" required>' +
                '<input type="text" class="form-control" id="percentage" value="%" disabled>' +
                '<label for="amountOfShares">Shares:</label>' +
                '<input type="number" class="form-control" id="amountOfShares" placeholder="Ex: 50" required>' +
                '<input type="text" class="form-control" id="shares" value="shares" disabled>' +
                '<button type="submit" class="btn">Submit</button>';
            $("#strategyCreatorStage2").html(selectionMenu);

        }
        else if (strategy == 'bollingerBands') {
            let selectionMenu = '<label for="movingAverage">Moving Average:</label>' +
                '<input type="number" class="form-control" id="movingAverage" placeholder="Ex: 3" required>' +
                '<select class="form-control">' +
                '<option>hours</option>' +
                '<option>minutes</option>' +
                '<option>seconds</option>' +
                '</select>' +
                '<label for="standardDeviations">Standard Deviations:</label>' +
                '<input type="number" class="form-control" id="standardDeviations" placeholder="Ex: 2" required>' +
                '<label for="plp">P/L%:</label>' +
                '<input type="number" class="form-control" id="plp" placeholder="Ex: 5" required>' +
                '<input type="text" class="form-control" id="percentage" value="%" disabled>' +
                '<label for="amountOfShares">Shares:</label>' +
                '<input type="number" class="form-control" id="amountOfShares" placeholder="Ex: 50" required>' +
                '<input type="text" class="form-control" id="shares" value="shares" disabled>' +
                '<button type="submit" class="btn">Submit</button>';
            $("#strategyCreatorStage2").html(selectionMenu);

        }
        else if (strategy == 'priceBreakout') {
            let selectionMenu = '<label for="fixedPeriod">Fixed Period:</label>' +
                '<input type="number" class="form-control" id="fixedPeriod" placeholder="Ex: 30" required>' +
                '<select class="form-control">' +
                '<option>hours</option>' +
                '<option selected>minutes</option>' +
                '<option>seconds</option>' +
                '</select>' +
                '<label for="fitOccurances">Fit Occurances:</label>' +
                '<input type="number" class="form-control" id="fitOccurances" placeholder="Ex: 3" required>' +
                '<input type="text" class="form-control" id="occurances" value="occurances" disabled>' +
                '<label for="stock">P/L%:</label>' +
                '<input type="number" class="form-control" id="plp" placeholder="Ex: 1" required>' +
                '<input type="text" class="form-control" id="percentage" value="%" disabled>' +
                '<label for="amountOfShares">Shares:</label>' +
                '<input type="number" class="form-control" id="amountOfShares" placeholder="Ex: 50" required>' +
                '<input type="text" class="form-control" id="shares" value="shares" disabled>' +
                '<button type="submit" class="btn">Submit</button>';
            $("#strategyCreatorStage2").html(selectionMenu);
        }
        else if (strategy == 'user') {
            let selectionMenu = '<label for="username">Username:</label>' +
                '<input type="text" class="form-control" id="username" required>' +
                '<label for="password">Password:</label>' +
                '<input type="text" class="form-control" id="password" required>' +
                '<button type="submit" class="btn" id="submit">Submit</button>';
            $("#strategyCreatorStage2").html(selectionMenu);
        }
    });

    $('#strategyCreatorForm').submit(function() {
       let name = $('#stockTag').val();
       let username = $('#username').val();
       let password = $('#password').val();
       let data = '{ "name":"'+name+'", "username":"'+username+'", "password":"'+password+'" }';

       $.ajax({
           url: "http://localhost:8081",
           crossOrigin: true,
           type: "POST",
           data: data,
           contentType: "application/json"
       })
    });

});