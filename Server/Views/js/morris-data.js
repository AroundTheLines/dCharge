$(function() {

    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2010 Q1',
            heartrate: 74,
        }, {
            period: '2010 Q2',
            heartrate: 78,
        }, {
            period: '2010 Q3',
            heartrate: 90,
        }, {
            period: '2010 Q4',
            heartrate: 140,
        }, {
            period: '2011 Q1',
            heartrate: 125,
        }, {
            period: '2011 Q2',
            heartrate: 115,
        }, {
            period: '2011 Q3',
            heartrate: 97,
        }, {
            period: '2011 Q4',
            heartrate: 86,
        }, {
            period: '2012 Q1',
            heartrate: 76,
        }, {
            period: '2012 Q2',
            heartrate: 69,
        }],
        xkey: 'period',
        ykeys: ['heartrate'],
        labels: ['heartrate'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });

    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Download Sales",
            value: 12
        }, {
            label: "In-Store Sales",
            value: 30
        }, {
            label: "Mail-Order Sales",
            value: 20
        }],
        resize: true
    });

    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: '2006',
            a: 100,
            b: 90
        }, {
            y: '2007',
            a: 75,
            b: 65
        }, {
            y: '2008',
            a: 50,
            b: 40
        }, {
            y: '2009',
            a: 75,
            b: 65
        }, {
            y: '2010',
            a: 50,
            b: 40
        }, {
            y: '2011',
            a: 75,
            b: 65
        }, {
            y: '2012',
            a: 100,
            b: 90
        }],
        xkey: 'y',
        ykeys: ['a', 'b'],
        labels: ['Series A', 'Series B'],
        hideHover: 'auto',
        resize: true
    });
    
});
