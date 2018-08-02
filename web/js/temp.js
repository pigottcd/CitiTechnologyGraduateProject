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
function priceToPrices(elm) {
    return elm.price;
}
function timeToTimes(elm) {
    return obj;
}
function dateTimeToDate(obj) {
    let year = obj.year;
    let month = obj.monthValue;
    let day = obj.dayOfMonth;
    let hours = obj.hour;
    let minutes = obj.minute;
    let seconds = obj.second;
    let milliseconds = obj.nano*(0.000001);
    let date = new Date(year, month, day, hours, minutes, seconds, milliseconds);
    return date;
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
        "order": [[0,"desc"]],
        "initComplete": function(settings, json) {
            $('#strategyTable').on("click", ".cloneStrategyButton", function () {
                let strategyDataFromRow = strategyTable.row($(this).parents("tr")).data();
                populateStrategyCreatorFields(strategyDataFromRow);
            });

            $('#strategyTable').on("click", ".terminateStrategyButton", function () {
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
                let rowID = strategyTable.row(this).data()['id'];
                orderTable.ajax.url("http://localhost:8081/orders/strategy_id/"+rowID.toString()+"/").load();
            });

            // get latest strategy id
            let lastRowID = strategyTable.row( ':first', {order: 'applied'}).data()['id'];
            let orderTable = $('#orderTable').DataTable({
                "ajax":{
                    "url":"http://localhost:8081/orders/strategy_id/"+lastRowID.toString()+"/",
                    "dataSrc":""
                },
                "columnDefs": [
                    {
                        "targets": [1],
                        "render": function(data, type, row) {
                            if (data==true) {
                                return "Buy";
                            }
                            else {
                                return "Sell";
                            }
                        }

                    },
                    {
                        "targets": [5],
                        "render": function(data) {
                            return dateTimeToDate(data);
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
                ],
            });
            orderTable.on('draw', function() {
                console.log('updated');
            });
            setInterval(function() {
                orderTable.ajax.reload();
            }, 5000);

        }
    })
});