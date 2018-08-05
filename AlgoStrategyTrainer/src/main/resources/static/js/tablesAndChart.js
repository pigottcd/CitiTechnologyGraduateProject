// converts time in seconds to a time and a time unit
function timeInSecondsToInUnit (time) {
    if (time <= 0) {
        return null;
    } else if (time/60 < 1) {
        return [time, "seconds"];
    } else if (time/3600 < 1) {
        return [time/60, "minutes"];
    }
    return [time/3600, "hours"];
}

// populates streategy creator fields when cloning a strategies settings
function populateStrategyCreatorFields (strategyData) {
    // pull settings from strategy data
    let strategyType = strategyData['type'];
    let stockTag = strategyData['ticker'];
    let profitLossPercentage = strategyData['pandL'];
    let amountOfShares = strategyData['quantity'];
    
    // set (strategy independent) streategy creator inputs to settings from strategy data
    $('#stockTag').val(stockTag);
    $('#profitLossPercentage').val(profitLossPercentage*100);
    $('#amountOfShares').val(amountOfShares);
    
    // set (strategy dependent) strategy creator inputs
    if (strategyType==='TwoMovingAverages') {
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

// "terminate" strategy by sending delete ajax call to rest api when terminating a strategy
function terminateStrategy(data, table) {
    let url = '/strategies/strategy_id/' + data['id'].toString();
    $.ajax({
        url: url,
        type: 'DELETE'
    }).then(function () {
        table.ajax.reload(null, false);
    })
}

// profit and loss calculator
function pricesToPAndL(data, type) {

    // pAndL starts at zero
    let pAndL = [0];
    // use dif to keep track off previous iterations pnl, to be able to calculate cumulative pnl
    let dif = 0;
    for (idx = 0; idx < data.length-1; idx=idx+2) {

        // pnl for a pair = sell price - buy price
        // if the first order of the pair was a buy, subtract that from second order of the pair
        if (type[idx]) {
            dif = dif + (data[idx+1]-data[idx]);
        }
        // if the first order of the pair was a sell, second order of the pair gets subtracted from that
        else {
            dif = dif + (data[idx]-data[idx+1]);
        }

        pAndL.push(dif);
        pAndL.push(null);
    }
    return pAndL;
}

// converts a dateTime java object into a date js object
function dateTimeToDate(obj) {
    let year = obj.year;
    let month = obj.monthValue-1;
    let day = obj.dayOfMonth;
    let hours = obj.hour;
    let minutes = obj.minute;
    let seconds = obj.second;
    let milliseconds = obj.nano*(0.000001);
    return new Date(year, month, day, hours, minutes, seconds, milliseconds);
}

// runs when the page has finished loading
$(document).ready(function() {
    
    // flag boolean for keeping current row on interval-ed ajax updates
    strategyTableRowClicked = false;
    
    // create and push table of strategies to table with id="strategyTable" in HTML
    let strategyTable = $('#strategyTable').DataTable({
        "ajax": {
            "url": "/strategies/",
            "dataSrc": ""
        },
        // runs when a row is created
        "createdRow": function (row, data) {

            // if the strategy is active, add bootstrap class to color the row green
            if (data['active']) {
                $(row).addClass("table-success");
            }
            // if the strategy isn't active, add bootstrap class to color the row red
            else {
                $(row).addClass("table-danger");
            }

            // if a row has been clicked and the current row matches the clicked rows id
            if (strategyTableRowClicked && data['id'] === rowID) {
                // keep the row outlined
                $(row).css({"outline-style":"solid", "outline-color":"rgba(0, 0, 0, 0.275)", "outline-offset":"-3px"});
                // keep the rows outlined class
                $(row).addClass("outlined");
            }

            if (!strategyTableRowClicked) {
                if (typeof last === "undefined") {
                    $(row).css({"outline-style":"solid", "outline-color":"rgba(0, 0, 0, 0.275)", "outline-offset":"-3px"});
                    // keep the rows outlined class
                    $(row).addClass("outlined");
                    last = row;
                }
                else {
                    $(last).css({"outline-style":"", "outline-color":"", "outline-offset":""}).removeClass("outlined");
                    $(row).css({"outline-style":"solid", "outline-color":"rgba(0, 0, 0, 0.275)", "outline-offset":"-3px"});
                    // keep the rows outlined class
                    $(row).addClass("outlined");
                    last = row;

                }
                rowID = data['id'];
            }

        },
        // settings for columns
        "columnDefs": [
            {
                "width": "10px",
                "targets": 4
            },
            {
                "targets": [3],
                "visible": false
            },
            // render clone and terminate buttons as last column on the table
            {
                "targets": [-1],
                "data": null,
                "defaultContent": "",
                "render": function (data) {
                    if (data['active']) {
                        return "<div class='form-inline mx-auto'><button class='cloneStrategyButton'>Clone</button><div class='mx-auto form-inline'><button class='terminateStrategyButton'>Terminate</button></div></div>";
                    }
                    else {
                        return "<div class='form-inline mx-auto'><button class='cloneStrategyButton'>Clone</button><div class='mx-auto form-inline'><p>Terminated</p></div></div>";
                    }
                }

            }
        ],
        "scrollY": "225px",
        "scrollX": false,
        "paging": false,
        "searching": false,
        columns: [
            {data: 'id', title: 'ID'},
            {data: 'type', title: 'Strategy Type'},
            {data: 'ticker', title: 'Ticker Symbol', width: 25 },
            {data: 'active', title: 'Active'},
            {data: 'quantity', title: 'Quantity'},
            {data: 'shortPeriod', title: 'Short Period', width: 50},
            {data: 'longPeriod', title: 'Long Period', width: 50},
            {data: 'pandL', title: 'P/L'},
            {data: null}
        ],
        // sets the order of the table on initialization
        "order": [[0,"desc"]],
        // runs once the table has fully initialized
        "initComplete": function() {
            let strategyTableDOM =  $('#strategyTable');
            // click listener for clone button
            strategyTableDOM.on("click", ".cloneStrategyButton", function () {
                // pull data from row
                let strategyDataFromRow = strategyTable.row($(this).parents("tr")).data();
                // send data to function to populate strategy creator inputs
                populateStrategyCreatorFields(strategyDataFromRow);

                let overlay = $('#overlay');
                overlay.fadeTo(0, 1);
                // scroll the page up to the strategy creator
                $('html, body').animate({
                    scrollTop: 0
                }, 500);
                overlay.delay(2000).fadeTo(1000, 0);
            });
            // click listener for terminate button
            strategyTableDOM.on("click", ".terminateStrategyButton", function () {
                let strategyDataFromRow = strategyTable.row($(this).parents("tr")).data();
                terminateStrategy(strategyDataFromRow, strategyTable);
            });

            strategyTableRowClicked = false;
            strategyTableDOM.on("click", "tr", function() {
                strategyTableRowClicked = true;
                if (typeof rowID != "undefined") {
                    if (rowID == strategyTable.row(this).data()['id']) {
                        return;
                    }
                    $('#strategyTable tr.outlined').css({"outline-style":"", "outline-color":"", "outline-offset":""}).removeClass("outlined");
                }
                $(this).css({"outline-style":"solid", "outline-color":"rgba(0, 0, 0, 0.275)", "outline-offset":"-3px"});
                $(this).addClass("outlined");
                rowID = strategyTable.row(this).data()['id'];
                orderTable.ajax.url("/orders/strategy_id/"+rowID.toString()+"/").load();
                $('#currentStrategy').text("Strategy "+rowID.toString()+" Profit and Loss");
                $('#currentOrders').text("Strategy "+rowID.toString()+" Orders");
                $('#currentOrders').fadeOut(250).fadeIn(250).fadeOut(250).fadeIn(250);
                $('#currentStrategy').fadeOut(250).fadeIn(250).fadeOut(250).fadeIn(250);
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
                url = "/orders/strategy_id/"+id+"/";
                orderTable.ajax.url(url).load();
                $('#currentStrategy').text("Strategy "+id+" Profit and Loss");
                $('#currentOrders').text("Strategy "+id+" Orders");
            });

            // get latest strategy id
            let firstRowID = strategyTable.row( ':first', {order: 'applied'}).data()['id'].toString();

            $('#currentStrategy').text("Strategy "+firstRowID+ " Profit and Loss");
            $('#currentOrders').text("Strategy "+firstRowID+" Orders");
            let orderTable = $('#orderTable').DataTable({
                "ajax":{
                    "url":"/orders/strategy_id/"+firstRowID+"/",
                    "dataSrc":""
                },
                "columnDefs": [
                    {
                        "targets": [1],
                        "width": 30,
                        "render": function(data) {
                            if (data) {
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
                            return dateTimeToDate(data).toDateString();
                        }
                    },
                    {
                        "targets": [6],
                        "render": function(data) {
                            return dateTimeToDate(data).toLocaleTimeString();
                        }
                    },
                    {
                        "targets": [7],
                        "render": function() {
                            return "Filled";
                        }
                    }
                ],
                "scrollY": "225px",
                "paging": false,
                "searching": false,
                columns: [
                    { data: 'id', title: 'ID' },
                    { data: 'buy', title: 'Buy/Sell', width: 10 },
                    { data: 'price', title: 'Price'},
                    { data: 'size', title: 'Size'},
                    { data: 'stock', title: 'Ticker Symbol', "width": 25 },
                    { data: 'time', title: 'Date' },
                    { data: 'time', title: 'Time'},
                    { data: 'status', title: 'Status' },
                ],
                "initComplete": function() {
                    let prices = orderTable.column(2).data();
                    let buySells = orderTable.column(1).data();
                    let pAndL = pricesToPAndL(prices, buySells);
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
                                borderWidth: 2,
                                fill: false,
                                lineTension: 0,
                                label: "Price of Stock per Share",
                                yAxisID: "y-axis-1"

                            },
                                {
                                    data: pAndL,
                                    borderColor: 'rgba(40, 167, 69, 1)',
                                    backgroundColor: 'rgba(40, 167, 69, .5)',
                                    borderWidth: 2,
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
                            responsive: true,
                            maintainAspectRatio: false,
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
                                        labelString: "P r i c e",
                                        fontColor: 'rgba(0, 123, 255, 1)',
                                        lineHeight: 1.5,
                                        fontSize: 16
                                    },
                                    ticks: {
                                        beginAtZero: false,
                                        callback: function(value) {
                                            return '$' + value;
                                        }
                                    },
                                    position: "left",
                                    id: "y-axis-1"
                                },
                                    {
                                        scaleLabel: {
                                            display: true,
                                            labelString: "P r o f i t",
                                            fontColor: 'rgba(40, 167, 69, 1)',
                                            lineHeight: 1.5,
                                            fontSize: 16
                                        },
                                        ticks: {
                                            beginAtZero: false,
                                            callback: function(value) {
                                                return '$' + value;
                                            }
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

                        let buySells = orderTable.column(1).data();
                        let prices = orderTable.column(2).data();
                        let pAndL = pricesToPAndL(prices, buySells);
                        let dates = orderTable.column(5).data();
                        dates = dates.map(dateTimeToDate);
                        chart.data.labels = dates;
                        chart.data.datasets[0].data = prices;
                        chart.data.datasets[1].data = pAndL;

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
