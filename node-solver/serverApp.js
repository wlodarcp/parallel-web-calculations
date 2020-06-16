// konfiguracja serwera express do serwowania strony html wraz ze skryptem obliczajacym
const express = require('express');
const app = express();
const port = process.env.PORT || 3000;
const bodyParser = require('body-parser');
const path = require('path');
app.use(bodyParser.json());
app.use(express.static(__dirname + '/View'));
app.use(express.static(__dirname + '/Script'));

app.use(function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.get('/web', function(req, res) {
    res.sendFile(path.join(__dirname+'/View/website.html'));
});

app.listen(port);
console.log('SERVER: started at: '+port);
