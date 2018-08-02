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
function pricesToPAndL(data) {
    let pAndL = [0];
    let dif = 0;
    for (idx = 0; idx < data.length-1; idx=idx+2) {

        dif = dif + Math.abs(data[idx]-data[idx+1]);
        pAndL.push(dif);
        pAndL.push(null);
    }
    return pAndL;
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
        "initComplete": function(strategyTableSettings, strategyTableJson) {
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

            strategyTableRowClicked = false;
            $('#strategyTable').on("click", "tr", function() {
                strategyTableRowClicked = true;
                rowID = strategyTable.row(this).data()['id'];
                orderTable.ajax.url("http://localhost:8081/orders/strategy_id/"+rowID.toString()+"/").load();
            });
            strategyTable.on('draw', function() {
                let url;
                let id;
                if (strategyTableRowClicked) {
                    id = rowID.toString();
                }
                else {
                    id = strategyTable.row( ':first', {order: 'applied'}).data()['id'].toString();
                }
                url = "http://localhost:8081/orders/strategy_id/"+id+"/";
                orderTable.ajax.url(url).load();
                $('#currentStrategy').text("Strategy "+id);
            });

            // get latest strategy id
            let firstRowID = strategyTable.row( ':first', {order: 'applied'}).data()['id'].toString();
            $('#currentStrategy').text("Strategy "+firstRowID);
            let orderTable = $('#orderTable').DataTable({
                "ajax":{
                    "url":"http://localhost:8081/orders/strategy_id/"+firstRowID+"/",
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
                    },
                    {
                        "targets": [6],
                        "render": function() {
                            return "Filled";
                        }
                    }
                ],
                "scrollY": "300px",
                "paging": false,
                "searching": true,
                columns: [
                    { data: 'id', title: 'ID' },
                    { data: 'buy', title: 'Buy/Sell' },
                    { data: 'price', title: 'Price' },
                    { data: 'size', title: 'Size' },
                    { data: 'stock', title: 'Ticker Symbol' },
                    { data: 'time', title: 'Time' },
                    { data: 'status', title: 'Status' },
                ],
                "initComplete": function(orderTableSettings, orderTableJson) {
                    let prices = orderTable.column(2).data();
                    let pAndL = pricesToPAndL(prices);
                    let dates = orderTable.column(5).data();
                    dates = dates.map(dateTimeToDate);

                    let ctx = $('#pAndLChart')[0].getContext('2d');
                    let config = {
                        type: 'line',
                        data: {
                            labels: dates,
                            datasets: [{
                                data: prices,
                                borderColor: 'rgba(0, 123, 255, 1)',
                                borderWidth: 5,
                                fill: false,
                                lineTension: 0,
                                label: "Price of Stock per Share",
                                yAxisID: "y-axis-1"

                            },
                                {
                                    data: pAndL,
                                    borderColor: 'rgba(40, 167, 69, 1)',
                                    backgroundColor: 'rgba(40, 167, 69, .5)',
                                    borderWidth: 5,
                                    fill: true,
                                    lineTension: 0,
                                    steppedLine: true,
                                    spanGaps: true,
                                    label: "Profit/Loss per Share",
                                    yAxisID: "y-axis-2"
                                }
                                ]
                        },
                        options: {
                            title: {
                                display: true,
                                text: 'Value vs. Time'
                            },
                            legend: {
                                display: true
                            },
                            scales: {
                                yAxes: [{
                                    scaleLabel: {
                                        display: true,
                                        labelString: "P r i c e ( $ )",
                                        fontColor: 'rgba(0, 123, 255, 1)',
                                        lineHeight: 1.5,
                                        fontSize: 16
                                    },
                                    ticks: {
                                        beginAtZero: false
                                    },
                                    position: "left",
                                    id: "y-axis-1"
                                },
                                    {
                                        scaleLabel: {
                                            display: true,
                                            labelString: "P r o f i t ( $ )",
                                            fontColor: 'rgba(40, 167, 69, 1)',
                                            lineHeight: 1.5,
                                            fontSize: 16
                                        },
                                        ticks: {
                                            beginAtZero: false
                                        },
                                        gridLines: {
                                            drawOnChartArea: false
                                        },
                                        position: "right",
                                        id: "y-axis-2"
                                    }],
                                xAxes: [{
                                    scaleLabel: {
                                        display: false,
                                        labelString: "Time"
                                    },
                                    type: 'time',
                                    time: {
                                        unit: 'minute'
                                    }
                                }]
                            }
                        }
                    };
                    let chart = new Chart(ctx, config);
                    orderTable.on('draw', function() {

                        let prices = orderTable.column(2).data();
                        let pAndL = pricesToPAndL(prices);
                        let dates = orderTable.column(5).data();
                        dates = dates.map(dateTimeToDate);
                        chart.data.labels = dates;
                        chart.data.datasets[0].data = prices;
                        chart.data.datasets[1].data = pAndL

                        chart.update();

                    });
                }
            });



            setInterval(function() {
                orderTable.ajax.reload(null, false);
                strategyTable.ajax.reload(null, false);
            }, 5000);

        }
    })
});