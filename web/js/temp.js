function timeInSecondsToInUnit (time) {
    if (time/60 < 1) {
        return [time, "seconds"];
    }
    else {
        if (time/3600 < 1) {
            return [time/60, "minutes"];
        }
        else {
            return [time/3600, "hours"];
        }
    }

}
function populateStrategyCreatorFields (strategyData) {
    let strategyType = strategyData['type'];
    let stockTag = strategyData['ticker'];
    let profitLossPercentage = strategyData['pandL'];
    let amountOfShares = strategyData['quantity'];

    $('#stockTag').val(stockTag);
    $('#profitLossPercentage').val(profitLossPercentage*100);
    $('#amountOfShares').val(amountOfShares);

    if (strategyType=='TwoMovingAverages') {
        $('#strategySelector').val("twoMovingAverages");

        let longAverageInSeconds = strategyData['longPeriod'];
        let longAverageParams = timeInSecondsToInUnit(longAverageInSeconds);
        $('#longAverage').val(longAverageParams[0]);
        $('#longAverageTimeUnit').val(longAverageParams[1]);

        let shortAverageInSeconds = strategyData['shortPeriod'];
        let shortAverageParams = timeInSecondsToInUnit(shortAverageInSeconds);
        $('#shortAverage').val(shortAverageParams[0]);
        $('#shortAverageTimeUnit').val(shortAverageParams[1]);
    }
}
function terminateStrategy(data) {
    let url = 'http://localhost:8081/strategies/strategy_id/' + data['id'].toString();
    $.ajax({
        url: url,
        type: 'DELETE'
    })
}
function dateTimeToDates(elm) {
    let year = elm.time.year;
    let month = elm.time.monthValue;
    let day = elm.time.dayOfMonth;
    let hours = elm.time.hour;
    let minutes = elm.time.minute;
    let seconds = elm.time.second;
    let milliseconds = elm.time.nano*(0.000001);
    let date = new Date(year, month, day, hours, minutes, seconds, milliseconds);
    return date;
}
function priceToPrices(elm) {
    return elm.price;
}
function timeToTimes(elm) {
    return elm.time;
}
$(document).ready(function() {

    let strategyTable = $('#strategyTable').DataTable({
        "ajax": {
            "url": "http://localhost:8081/strategies/",
            "dataSrc": ""
        },
        "createdRow": function (row, data) {

            if (data['active'] == true) {
                $(row).addClass("table-success");
            }
            else {
                $(row).addClass("table-danger");
            }
        },
        "columnDefs": [
            {
                "targets": [3],
                "visible": false
            },
            {
                "targets": [-1],
                "data": null,
                "defaultContent": "",
                "render": function (data, type, row) {
                    if (data['active'] == true) {
                        return "<div class='form-inline mx-auto'><button class='cloneStrategyButton'>Clone</button><button class='terminateStrategyButton'>Terminate</button></div>";
                    }
                    else {
                        return "<div class='form-inline mx-auto'><button class='cloneStrategyButton'>Clone</button></div>";
                    }
                }

            }
        ],
        "scrollY": "300px",
        "paging": false,
        columns: [
            {data: 'id', title: 'ID'},
            {data: 'type', title: 'Strategy Type'},
            {data: 'ticker', title: 'Ticker Symbol'},
            {data: 'active', title: 'Active'},
            {data: 'quantity', title: 'Quantity'},
            {data: 'shortPeriod', title: 'Short Period'},
            {data: 'longPeriod', title: 'Long Period'},
            {data: 'pandL', title: 'P/L'},
            {data: null, "width": "150px"}
        ],
        "initComplete": function(settings, json) {
            $('.cloneStrategyButton').click(function () {
                let strategyDataFromRow = strategyTable.row($(this).parents("tr")).data();
                populateStrategyCreatorFields(strategyDataFromRow);
            });

            $('.terminateStrategyButton').click(function () {
                let strategyDataFromRow = strategyTable.row($(this).parents("tr")).data();
                let deleteUrl = 'http://localhost:8081/strategies/strategy_id/' + strategyDataFromRow['id'].toString();

                $.ajax({
                    url: deleteUrl,
                    type: 'DELETE'
                }).then(function () {
                    strategyTable.ajax.reload(null, false);
                })
            });

            $('#strategyTable').on("click", "tr", function() {
                console.log("works after");
            });
            $('#strategyTable tr').click(function() {
                console.log("clicked");
                let rowID = strategyTable.row(this).data()['id'];
                orderTable.ajax.url("http://localhost:8081/orders/strategy_id/"+rowID.toString()+"/").load();
            });

            // get latest strategy id
            let lastRowID = strategyTable.row( ':last', {order: 'applied'}).data()['id'];
            let orderTable = $('#orderTable').DataTable({
                "ajax":{
                    "url":"http://localhost:8081/orders/strategy_id/"+lastRowID.toString()+"/",
                    "dataSrc":""
                },
                "columnDefs": [
                    {
                        "targets": [1],
                        "render": function(data, type, row) {
                            if (data['buy']==true) {
                                return "Buy";
                            }
                            else {
                                return "Sell";
                            }
                        }

                    }
                ],
                "scrollY": "300px",
                "paging": false,
                columns: [
                    { data: 'id', title: 'ID' },
                    { data: 'buy', title: 'Buy/Sell' },
                    { data: 'price', title: 'Price' },
                    { data: 'size', title: 'Size' },
                    { data: 'stock', title: 'Ticker Symbol' },
                    { data: 'time', title: 'Time' },
                    { data: 'status', title: 'Status' },
                ]
            });
            setInterval(function() {
                orderTable.ajax.reload();
            }, 5000);

        }
    })
});