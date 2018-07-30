function addData(chart, label, data) {
    chart.data.labels.push(label);
    chart.data.datasets.forEach((dataset) => {
        dataset.data.push(data);
    });
    chart.update();
}

function removeData(chart) {
    chart.data.labels.pop();
    chart.data.datasets.forEach((dataset) => {
        dataset.data.pop();
    });
    chart.update();
}
// run code once the page has loaded
$(document).ready(function() {

    // start ajax call, then process the data
    $.ajax({
        url: "http://localhost:8081/",
        crossOrigin: true
    }).then(function(data) {
        // create table
        let table_body = '<table class="table table-striped table-sm"><thead><tr>';
        for (value in data[0]) {
            table_body+='<th>';
            table_body+=value;
            table_body+='</th>';
        }
        table_body+='</tr></thead><tbody>';
        for (idx = 0; idx < data.length; idx++) {
            table_body += '<tr class="clickableRow">';
            for (value in data[0]) {
                table_body += '<td>';
                table_body += data[idx][value];
                table_body += '</td>';
            }
            table_body += '</tr>';
        }
        table_body+='</tbody></table>';

        // push table to usersTable div tag
        $('#usersTable').html(table_body);

        // pull data for chart
        // work on implementation here, redundant json processing (already processed for table)
        let labels = [];
        let values = [];
        data.forEach(function(elm) {
            labels.push(elm.name);
            values.push(elm.id);
        });

        // create and push chart to myChart chart tag
        let ctx = document.getElementById("myChart").getContext('2d');
        myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'User ids',
                    data: values,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255,99,132,1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });

        $('.clickableRow').click(function() {
            var tableData = $(this).children("td").map(function () {
                return $(this).text();
            }).get();
            let labels = [];
            let values = [];
            labels.push(tableData[1]);
            values.push(parseInt(tableData[0]));

            //removeData(myChart);
            myChart.destroy();
            //addData(myChart, labels, values);
            let ctx = document.getElementById("myChart").getContext('2d');
            myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'User ids',
                        data: values,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255,99,132,1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    }
                }
            });


        });
    });

});