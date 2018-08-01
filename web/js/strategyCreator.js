function generateStrategySelectionParameters(strategy) {
    let parameter1HTML = '';
    let parameter2HTML = '';

    if (strategy == 'twoMovingAverages') {
        // build rest of selection menu
        parameter1HTML = '<label for="longAverage">Long Average:</label>' +
            '<input type="number" class="form-control" id="longAverage" placeholder="Ex: 3" required>' +
            '<select class="form-control" id="longAverageTimeUnit">' +
            '<option>hours</option>' +
            '<option>minutes</option>' +
            '<option>seconds</option>' +
            '</select>';
        parameter2HTML = '<label for="shortAverage">Short Average:</label>' +
            '<input type="number" class="form-control" id="shortAverage" placeholder="Ex: 30" required>' +
            '<select class="form-control" id="shortAverageTimeUnit">' +
            '<option>hours</option>' +
            '<option selected>minutes</option>' +
            '<option>seconds</option>' +
            '</select>';
    }
    else if (strategy == 'bollingerBands') {
        // build rest of selection menu
        parameter1HTML = '<label for="movingAverage">Moving Average:</label>' +
            '<input type="number" class="form-control" id="movingAverage" placeholder="Ex: 3" required>' +
            '<select class="form-control" id="movingAverageTimeUnit">' +
            '<option>hours</option>' +
            '<option>minutes</option>' +
            '<option>seconds</option>' +
            '</select>';
        parameter2HTML = '<label for="standardDeviations">Standard Deviations:</label>' +
            '<input type="number" class="form-control" id="standardDeviations" placeholder="Ex: 2" required>' +
            '<input type="text" class="form-control" id="deviations" value="deviations" disabled>';
    }
    else if (strategy == 'priceBreakout') {
        // build the rest of the selection menu
        parameter1HTML = '<label for="fixedPeriod">Fixed Period:</label>' +
            '<input type="number" class="form-control" id="fixedPeriod" placeholder="Ex: 30" required>' +
            '<select class="form-control" id="fixedPeriodTimeUnit">' +
            '<option>hours</option>' +
            '<option selected>minutes</option>' +
            '<option>seconds</option>' +
            '</select>';
        parameter2HTML = '<label for="fitOccurrences">Fit Occurrences:</label>' +
            '<input type="number" class="form-control" id="fitOccurrences" placeholder="Ex: 3" required>' +
            '<input type="text" class="form-control" id="occurrences" value="occurrences" disabled>';
    }
    else if (strategy == 'user') {
        // create the rest of the strategy creator
        parameter1HTML = '<label for="username">Username:</label>' +
            '<input type="text" class="form-control" id="username" required>';
        parameter2HTML = '<label for="password">Password:</label>' +
            '<input type="text" class="form-control" id="password" required>';
    }

        // push rest of selection menu
        $("#parameter1").html(parameter1HTML);
        $("#parameter2").html(parameter2HTML);

}
function timeInUnitToInSeconds(time, timeUnit) {
    if (timeUnit=='seconds') {
        return time;
    }
    else if (timeUnit=='minutes') {
        return time*60;
    }
    else if (timeUnit=='hours') {
        return time*60*60;
    }
}
function buildJSONFromForm(strategy) {
    // pull inputs
    let data = '';
    let stockTag = $('#stockTag').val();
    let profitLossPercentge = $('#profitLossPercentage').val()/100;
    let amountOfShares = $('#amountOfShares').val();

    if (strategy == 'twoMovingAverages') {
        let longAverage = $('#longAverage').val();
        // could just send longAverage in seconds, or send to business layer with time unit
        let longAverageTimeUnit = $('#longAverageTimeUnit').val();
        let longAverageInSeconds = timeInUnitToInSeconds(longAverage, longAverageTimeUnit);
        let shortAverage = $('#shortAverage').val();
        // could just send shortAverage in seconds, or send to business layer with time unit
        let shortAverageTimeUnit = $('#shortAverageTimeUnit').val();
        let shortAverageInSeconds = timeInUnitToInSeconds(shortAverage, shortAverageTimeUnit);

        // build json string
        data = '{ "type":"TwoMovingAverages",' +
            ' "ticker":"' + stockTag + '",' +
            ' "active":'+ true + ',' +
            ' "quantity":' + amountOfShares + ',' +
            ' "shortPeriod":' + shortAverageInSeconds + ',' +
            ' "longPeriod":' + longAverageInSeconds + ',' +
            ' "pandL":' + profitLossPercentge + ' }';
    }
    else if (strategy == 'bollingerBands') {
        let movingAverage = $('#movingAverage').val();
        // could just send movingAverage in seconds, or send to business layer with time unit
        let movingAverageTimeUnit = $('#movingAverageTimeUnit').val();
        let standardDeviations = $('#standardDeviations').val();

        // build json string
        data = '{ "stockTag":"' + stockTag +
            '", "strategy":"' + strategy +
            '", "movingAverage":"' + movingAverage +
            '", "movingAverageTimeUnit":"' + movingAverageTimeUnit +
            '", "standardDeviations":"' + standardDeviations +
            '", "profitLossPercentage":"' + profitLossPercentge +
            '", "amountOfShares":"' + amountOfShares +
            '" }';
    }
    else if (strategy == 'priceBreakout') {
        let fixedPeriod = $('#fixedPeriod').val();
        // could just send fixedPeriod in seconds, or send to business layer with time unit
        let fixedPeriodTimeUnit = $('#fixedPeriodTimeUnit').val();
        let fitOccurrences = $('#fitOccurrences').val();

        // build json string
        data = '{ "stockTag":"' + stockTag +
            '", "strategy":"' + strategy +
            '", "fixedPeriod":"' + fixedPeriod +
            '", "fixedPeriodTimeUnit":"' + fixedPeriodTimeUnit +
            '", "fitOccurrences":"' + fitOccurrences +
            '", "profitLossPercentage":"' + profitLossPercentge +
            '", "amountOfShares":"' + amountOfShares +
            '" }';
    }
    else if (strategy == 'user') {
        // pull inputs
        let name = $('#stockTag').val();
        let username = $('#username').val();
        let password = $('#password').val();

        // build json string
        data = '{ "name":"' + name +
            '", "username":"' + username +
            '", "password":"' + password +
            '" }';
    }
    console.log(data);
    return data;
}

function sendJSONToAPI(strategy, data) {
    let url = 'http:localhost:8081/';

    if (strategy!='user') {
        url += 'strategies/'
    }
    else {
        url += 'users/'
    }
    // strategy will be used to route to the right url
    $.ajax({
        url: url,
        crossOrigin: true,
        type: "POST",
        data: data,
        contentType: "application/json"
    });
}

// runs when page has been fully loaded
$(document).ready(function() {

    selectedStrategy = 'twoMovingAverages';

    // runs when the strategy selector
    $("#strategySelector").change(function() {

        // change selected strategy variable
        selectedStrategy = $("#strategySelector :selected").val();

        // populate selection menu
        generateStrategySelectionParameters(selectedStrategy);
    });

    // runs when form is submitted
    $('#strategyCreatorForm').submit(function() {

        // build json data from the form
        data = buildJSONFromForm(selectedStrategy);
        console.log(data);
        // send it to the rest api
        sendJSONToAPI(selectedStrategy, data);
    });
});