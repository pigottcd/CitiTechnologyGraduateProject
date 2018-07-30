// runs when page has been fully loaded
$(document).ready(function() {

    // runs when the strategy drop down changes value
    $("#strategySelector").change(function() {


        // pull the selected strategy
        strategy = $("#strategySelector :selected").val();

        // populate selection menu
        if (strategy =='twoMovingAverages') {
            
            // create rest of selection menu
            let parameter1 = '<label for="longAverage">Long Average:</label>' +
                '<input type="number" class="form-control" id="longAverage" placeholder="Ex: 3" required>' +
                '<select class="form-control" id="longAverageTimeUnit">' +
                '<option>hours</option>' +
                '<option>minutes</option>' +
                '<option>seconds</option>' +
                '</select>';
            let parameter2 = '<label for="shortAverage">Short Average:</label>' +
                '<input type="number" class="form-control" id="shortAverage" placeholder="Ex: 30" required>' +
                '<select class="form-control" id="shortAverageTimeUnit">' +
                '<option>hours</option>' +
                '<option selected>minutes</option>' +
                '<option>seconds</option>' +
                '</select>';
            let pnl = '<label for="profitLossPercentage">P/L%:</label>' +
                '<input type="number" class="form-control" id="profitLossPercentage" placeholder="Ex: 1" required>' +
                '<input type="text" class="form-control" id="percentage" value="%" disabled>';
            let amount = '<label for="amountOfShares">Shares:</label>' +
                '<input type="number" class="form-control" id="amountOfShares" placeholder="Ex: 50" required>' +
                '<input type="text" class="form-control" id="shares" value="shares" disabled>';
            let submit = '<button type="submit" class="btn">Submit</button>';
            
            // push rest of selection menu
            $("#parameter1").html(parameter1);
            $("#parameter2").html(parameter2);
            $("#pnl").html(pnl);
            $("#amount").html(amount);
            $("#submit").html(submit);

            $('#strategyCreatorForm').submit(function() {

                if (strategy=='twoMovingAverages') {
                    // pull inputs
                    let stockTag = $('#stockTag').val();
                    let longAverage = $('#longAverage').val();
                    // could just send longAverage in seconds, or send to business layer with time unit
                    let longAverageTimeUnit = $('#longAverageTimeUnit').val();
                    let shortAverage = $('#shortAverage').val();
                    // could just send shortAverage in seconds, or send to business layer with time unit
                    let shortAverageTimeUnit = $('#shortAverageTimeUnit').val();
                    let profitLossPercentge = $('#profitLossPercentage').val();
                    let amountOfShares = $('#amountOfShares').val();

                    // build json string
                    let data = '{ "stockTag":"'+stockTag+
                        '", "strategy":"'+strategy+
                        '", "longAverage":"'+longAverage+
                        '", "longAverageTimeUnit":"'+longAverageTimeUnit+
                        '", "shortAverage":"'+shortAverage+
                        '", "shortAverageTimeUnit":"'+shortAverageTimeUnit+
                        '", "profitLossPercentage":"'+profitLossPercentge+
                        '", "amountOfShares":"'+amountOfShares+
                        '" }';
                    console.log(data);

                    // send inputs to api
                    /*
                    $.ajax({
                        url: "http://localhost:8081",
                        crossOrigin: true,
                        type: "POST",
                        data: data,
                        contentType: "application/json"
                    })*/
                }
            });

        }
        else if (strategy == 'bollingerBands') {
            
            // create rest of selection menu
            let selectionMenu = '<label for="movingAverage">Moving Average:</label>' +
                '<input type="number" class="form-control" id="movingAverage" placeholder="Ex: 3" required>' +
                '<select class="form-control" id="movingAverageTimeUnit">' +
                '<option>hours</option>' +
                '<option>minutes</option>' +
                '<option>seconds</option>' +
                '</select>' +
                '<label for="standardDeviations">Standard Deviations:</label>' +
                '<input type="number" class="form-control" id="standardDeviations" placeholder="Ex: 2" required>' +
                '<label for="profitLossPercentage">P/L%:</label>' +
                '<input type="number" class="form-control" id="profitLossPercentage" placeholder="Ex: 5" required>' +
                '<input type="text" class="form-control" id="percentage" value="%" disabled>' +
                '<label for="amountOfShares">Shares:</label>' +
                '<input type="number" class="form-control" id="amountOfShares" placeholder="Ex: 50" required>' +
                '<input type="text" class="form-control" id="shares" value="shares" disabled>' +
                '<button type="submit" class="btn">Submit</button>';
            
            // push rest of selection menu to DOM
            $("#strategyCreatorStage2").html(selectionMenu);

            // send data on form submit
            $('#strategyCreatorForm').submit(function() {
                if (strategy == 'bollingerBands') {
                    // pull inputs
                    let stockTag = $('#stockTag').val();
                    let movingAverage = $('#movingAverage').val();
                    // could just send movingAverage in seconds, or send to business layer with time unit
                    let movingAverageTimeUnit = $('#movingAverageTimeUnit').val();
                    let standardDeviations = $('#standardDeviations').val();
                    let profitLossPercentge = $('#profitLossPercentage').val();
                    let amountOfShares = $('#amountOfShares').val();
                    // build json string
                    let data = '{ "stockTag":"' + stockTag +
                        '", "strategy":"' + strategy +
                        '", "movingAverage":"' + movingAverage +
                        '", "movingAverageTimeUnit":"' + movingAverageTimeUnit +
                        '", "standardDeviations":"' + standardDeviations +
                        '", "profitLossPercentage":"' + profitLossPercentge +
                        '", "amountOfShares":"' + amountOfShares +
                        '" }';
                    console.log(data);

                    // send inputs to api
                    /*
                    $.ajax({
                        url: "http://localhost:8081",
                        crossOrigin: true,
                        type: "POST",
                        data: data,
                        contentType: "application/json"
                        })*/
                }
            });
        }
        else if (strategy == 'priceBreakout') {

            // create the rest of the selection menu
            let selectionMenu = '<label for="fixedPeriod">Fixed Period:</label>' +
                '<input type="number" class="form-control" id="fixedPeriod" placeholder="Ex: 30" required>' +
                '<select class="form-control" id="fixedPeriodTimeUnit">' +
                '<option>hours</option>' +
                '<option selected>minutes</option>' +
                '<option>seconds</option>' +
                '</select>' +
                '<label for="fitOccurrences">Fit Occurrences:</label>' +
                '<input type="number" class="form-control" id="fitOccurrences" placeholder="Ex: 3" required>' +
                '<input type="text" class="form-control" id="occurrences" value="occurrences" disabled>' +
                '<label for="stock">P/L%:</label>' +
                '<input type="number" class="form-control" id="profitLossPercentage" placeholder="Ex: 1" required>' +
                '<input type="text" class="form-control" id="percentage" value="%" disabled>' +
                '<label for="amountOfShares">Shares:</label>' +
                '<input type="number" class="form-control" id="amountOfShares" placeholder="Ex: 50" required>' +
                '<input type="text" class="form-control" id="shares" value="shares" disabled>' +
                '<button type="submit" class="btn">Submit</button>';

            // push rest of the selection menu to DOM
            $("#strategyCreatorStage2").html(selectionMenu);

            // send data on form submit
            $('#strategyCreatorForm').submit(function() {
                if (strategy == 'priceBreakout') {
                    // pull inputs
                    let stockTag = $('#stockTag').val();
                    let fixedPeriod = $('#fixedPeriod').val();
                    // could just send fixedPeriod in seconds, or send to business layer with time unit
                    let fixedPeriodTimeUnit = $('#fixedPeriodTimeUnit').val();
                    let fitOccurrences = $('#fitOccurrences').val();
                    let profitLossPercentge = $('#profitLossPercentage').val();
                    let amountOfShares = $('#amountOfShares').val();

                    // build json string
                    let data = '{ "stockTag":"' + stockTag +
                        '", "strategy":"' + strategy +
                        '", "fixedPeriod":"' + fixedPeriod +
                        '", "fixedPeriodTimeUnit":"' + fixedPeriodTimeUnit +
                        '", "fitOccurrences":"' + fitOccurrences +
                        '", "profitLossPercentage":"' + profitLossPercentge +
                        '", "amountOfShares":"' + amountOfShares +
                        '" }';
                    console.log(data);

                    // send inputs to api
                    /*
                    $.ajax({
                        url: "http://localhost:8081",
                        crossOrigin: true,
                        type: "POST",
                        data: data,
                        contentType: "application/json"
                    })*/
                }
            });

        }
        else if (strategy == 'user') {
            // create the rest of the strategy creator
            let selectionMenu = '<label for="username">Username:</label>' +
                '<input type="text" class="form-control" id="username" required>' +
                '<label for="password">Password:</label>' +
                '<input type="text" class="form-control" id="password" required>' +
                '<button type="submit" class="btn" id="submit">Submit</button>';

            // push the rest of the strategy creator to the DOM
            $("#strategyCreatorStage2").html(selectionMenu);

            // send data on form submit
            $('#strategyCreatorForm').submit(function() {
                if (strategy == 'user') {
                    // pull inputs
                    let name = $('#stockTag').val();
                    let username = $('#username').val();
                    let password = $('#password').val();

                    // build json string
                    let data = '{ "name":"' + name +
                        '", "username":"' + username +
                        '", "password":"' + password +
                        '" }';

                    // send inputs to api
                    $.ajax({
                        url: "http://localhost:8081",
                        crossOrigin: true,
                        type: "POST",
                        data: data,
                        contentType: "application/json"
                    });
                }
            });

        }
    });
});