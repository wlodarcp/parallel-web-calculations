const url = 'http://127.0.0.1:8080/calculations';
let count = 0;

async function getCalculatePost() {
    let data = await axios.get(url);
    data = data.data;
    if(data) {
        count++;
        document.getElementById("taskID").innerText = "Jest to " + count + " Zadanie wykonante w ramach tej sesji Aktualnie dokonuje obliczania zadania o ID: " + data.id;
        console.log("Rozpoczeto wykonywanie " + count + " zadania wykonane w twojej przegladarce");
        let calculationResult = math.lsolve(data.matrix, data.array).map(function(val){
            return val[0];
        });
        let result = {id: data.id, resolved: calculationResult};
        sleep(2000);
        console.log(result);
        console.log("Zadanie zostalo ukonczone i wyniki zostana odeslane do serwera");
        await axios.post(url, result);
        await getCalculatePost();
    } else {
        document.getElementById("taskID").innerText = "Brak zadań na serwerze";
    }
}

function sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < milliseconds);
}

getCalculatePost();