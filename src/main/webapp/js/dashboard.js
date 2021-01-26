/* globals Chart:false, feather:false */

(function () {
    'use strict'

    feather.replace()

    // // Graphs
    // var ctx = document.getElementById('myChart')
    // // eslint-disable-next-line no-unused-vars
    // var myChart = new Chart(ctx, {
    //     type: 'line',
    //     data: {
    //         labels: [
    //             'Sunday',
    //             'Monday',
    //             'Tuesday',
    //             'Wednesday',
    //             'Thursday',
    //             'Friday',
    //             'Saturday'
    //         ],
    //         datasets: [{
    //             data: [
    //                 15339,
    //                 21345,
    //                 18483,
    //                 24003,
    //                 23489,
    //                 24092,
    //                 12034
    //             ],
    //             lineTension: 0,
    //             backgroundColor: 'transparent',
    //             borderColor: '#007bff',
    //             borderWidth: 4,
    //             pointBackgroundColor: '#007bff'
    //         }]
    //     },
    //     options: {
    //         scales: {
    //             yAxes: [{
    //                 ticks: {
    //                     beginAtZero: false
    //                 }
    //             }]
    //         },
    //         legend: {
    //             display: false
    //         }
    //     }
    // })
}())

// Highlight Selected Bar
$(".sidebar-sticky li a").on("click", function() {
    $(".sidebar-sticky li a").removeClass("active");
    this.classList.add("active");
});


var d = 10;
var order = $("#order");
order.on("click", {eat: 12}, updateChart);
order.on("click", {v1: 6}, updateTable);


function updateChart(event) {
    console.log(12312);
    console.log(event.data.eat);
    var da=event.data.eat;
    console.log(da);
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {

        var data = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['Work',     11],
            ['Eat',      da],
            ['Commute',  2],
            ['Watch TV', 2],
            ['Sleep',    7]
        ]);

        var options = {
            title: 'My Daily Activities'
        };

        var chart = new google.visualization.PieChart(document.getElementById('mychart'));

        chart.draw(data, options);
    }
}


function updateTable(event) {
    console.log("Table!");
    google.charts.load('current', {'packages':['table']});
    google.charts.setOnLoadCallback(drawTable);

    function drawTable() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Name');
        data.addColumn('number', 'Salary');
        data.addColumn('boolean', 'Full Time Employee');
        data.addRows([
            ['Mike',  {v: 10000, f: '$10,000'}, true],
            ['me',   {v:event.data.v1,   f: String(event.data.v1)},  false],
            ['Alice', {v: 12500, f: '$12,500'}, true],
            ['Bob',   {v: 7000,  f: '$7,000'},  true],
            ['Mike',  {v: 10000, f: '$10,000'}, true],
            ['me',   {v:event.data.v1,   f: String(event.data.v1)},  false],
            ['Alice', {v: 12500, f: '$12,500'}, true],
            ['Bob',   {v: 7000,  f: '$7,000'},  true],
            ['Mike',  {v: 10000, f: '$10,000'}, true],
            ['me',   {v:event.data.v1,   f: String(event.data.v1)},  false],
            ['Alice', {v: 12500, f: '$12,500'}, true],
            ['Bob',   {v: 7000,  f: '$7,000'},  true],
            ['Mike',  {v: 10000, f: '$10,000'}, true],
            ['me',   {v:event.data.v1,   f: String(event.data.v1)},  false],
            ['Alice', {v: 12500, f: '$12,500'}, true],
            ['Bob',   {v: 7000,  f: '$7,000'},  true],
            ['Mike',  {v: 10000, f: '$10,000'}, true],
            ['me',   {v:event.data.v1,   f: String(event.data.v1)},  false],
            ['Alice', {v: 12500, f: '$12,500'}, true],
            ['Bob',   {v: 7000,  f: '$7,000'},  true]

        ]);

        var table = new google.visualization.Table(document.getElementById('mytable'));

        table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});
    }
}

