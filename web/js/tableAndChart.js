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
$(document).ready(function() {
    // strategy table
    $.ajax({
        url: "http://localhost:8081/strategies/",
        crossOrigin: true
    }).then(function(strategyData){
        let strategyTable = $('#strategyTable').DataTable({
            "createdRow": function(row, data) {
                console.log(data);
                if (data['active']==true) {
                    $(row).addClass("table-success");
                }
                else {
                    $(row).addClass("table-danger");
                }
            },
            data: strategyData,
            columns: [
                { data: 'id', title: 'ID' },
                { data: 'type', title: 'Strategy Type' },
                { data: 'ticker', title: 'Ticker Symbol' },
                { data: 'active', title: 'Active' },
                { data: 'quantity', title: 'Quantity' },
                { data: 'shortPeriod', title: 'Short Period' },
                { data: 'longPeriod', title: 'Long Period' },
                { data: 'pandL', title: 'P/L' },

            ]
        });

        // get latest strategy id
        let lastRowID = strategyTable.row( ':last', {order: 'applied'}).data()['id'];

        // order table and pnl graph
        $.ajax({
            url: "http://localhost:8081/orders/strategy_id/"+lastRowID.toString(),
            crossOrigin: true
        }).then(function(orderData) {


            let orderTable = $('#orderTable').DataTable({
                data: orderData,
                columns: [
                    { data: 'id', title: 'id' },
                    { data: 'buy', title: 'buy' },
                    { data: 'price', title: 'price' },
                    { data: 'size', title: 'size' },
                    { data: 'stock', title: 'stock' },
                    { data: 'time', title: 'time' },
                    { data: 'status', title: 'status' },
                ]
            });

            // graph
            let dates = orderData.map(dateTimeToDates);
            let prices = orderData.map(priceToPrices);

            let ctx = $('#priceChart')[0].getContext('2d');
            let config = {
                type: 'line',
                data: {
                    labels: dates,
                    datasets: [{
                        data: prices,
                        backgroundColor: 'rgba(0, 119, 204, 0.3)'
                    }]
                },
                options: {
                    scales: {
                        xAxes: [{
                            type: 'time',
                            time: {
                                unit: 'minute'
                            }
                        }]
                    }
                }
            };
            let chart = new Chart(ctx, config);
        })
    });
});