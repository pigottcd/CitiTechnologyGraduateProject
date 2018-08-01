$(document).ready(function() {

    // strategy table
    $.ajax({
        url: "http://localhost:8081/strategies/",
        crossOrigin: true
    }).then(function(strategyData){
        let strategyTable = $('#strategyTable').DataTable({
            data: strategyData,
            columns: [
                { data: 'id', title: 'id' },
                { data: 'type', title: 'type' },
                { data: 'ticker', title: 'ticker' },
                { data: 'active', title: 'active' },
                { data: 'quantity', title: 'quantity' },
                { data: 'shortPeriod', title: 'shortPeriod' },
                { data: 'longPeriod', title: 'longPeriod' },
                { data: 'pandL', title: 'pandL' },

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

            // pnl graph
            console.log(orderData);
            let dates = orderData.map(function (elm) {
                let year = elm.time.year;
                let month = elm.time.monthValue;
                let day = elm.time.dayOfMonth;
                let hours = elm.time.hour;
                let minutes = elm.time.minute;
                let seconds = elm.time.second;
                let milliseconds = elm.time.nano*(0.000001);
                console.log(year, month, day, hours, minutes, seconds, milliseconds);
                let date = new Date(year, month, day, hours, minutes, seconds, milliseconds);
                return date;
            });
            let prices = orderData.map(function (elm) {
                return elm.price;
            });
            console.log(dates);
            console.log(prices);



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