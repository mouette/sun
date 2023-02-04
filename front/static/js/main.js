htmx.logAll();
htmx.on("htmx:afterRequest", function (evt) {
    if (evt.detail.target.id == "viz") {
        let data = JSON.parse(evt.detail.xhr.responseText);
        draw(data);
    }
});
// set the dimensions and margins of the graph
let viz = document.getElementById("viz");
let inited = false;
var margin = { top: 10, right: 30, bottom: 30, left: 40 },
    width = viz.offsetWidth - margin.left - margin.right,
    height = 400 - margin.top - margin.bottom;

// append the svg object to the body of the page
var svg = d3.select("#viz")
    .append("svg")
    .attr("display", "block")
    .attr("margin", "auto")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform",
        "translate(" + margin.left + "," + margin.top + ")");

// get the data
function draw(data) {
    //let data = JSON.parse(document.getElementById("params-target").innerHTML);

    var x = d3.scaleBand()
        .range([0, width])
        .domain(data.map(function (d) { return d.month; }))
        .padding(0.2);

    var y = d3.scaleLinear()
        .domain([0, 200])
        .range([height, 0]);

    if (!inited) {
        svg.append("g")
            .attr("transform", "translate(0," + height + ")")
            .call(d3.axisBottom(x));

        svg.append("g")
            .call(d3.axisLeft(y));

        inited = true;
    }

    // Bars
    var u = svg.selectAll("rect")
        .data(data);
    u
        .enter()
        .append("rect")
        .merge(u)
        .transition()
        .duration(100)
        .attr("x", function (d) { return x(d.month); })
        .attr("y", function (d) { return y(d.e); })
        .attr("width", x.bandwidth())
        .attr("height", function (d) { console.log(d); return height - y(d.e); })
        .attr("fill", "#69b3a2")
}
