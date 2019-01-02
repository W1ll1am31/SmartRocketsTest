const Http = new XMLHttpRequest();
let positionData;
let runAlgo = false;
let generationCount = 20;
let currentGeneration = 0;
let lifespan = 100;
let currentLife = 0;
var genSlider;
var lifeSlider;
var popSlider;

function setup() {
    createCanvas(800, 800);
    genSlider = createSlider(1, 50, 5);
    lifeSlider = createSlider(10, 500, 250);
    popSlider = createSlider(1, 50, 10);
}

function draw() {
    background(0);
    if(runAlgo) {
        for(let rocket of positionData[currentGeneration]) {
            show(rocket[currentLife])
        }
        currentLife++;
        if(currentLife >= lifespan) {
            currentLife = 0;
            currentGeneration++;
        }
        if(currentGeneration >= generationCount) {
            currentGeneration = 0;
            runAlgo = false
        }
    }
    fill(255)
    ellipse(400, 50, 20, 20);
}

function show(rocket) {
    // push and pop allow's rotating and translation not to affect other objects
    push();
    //color customization of rockets
    noStroke();
    fill(255, 150);
    //translate to the postion of rocket
    translate(rocket.x, rocket.y);
    //rotatates to the angle the rocket is pointing
    rotate(rocket.heading);
    //creates a rectangle shape for rocket
    rectMode(CENTER);
    rect(0, 0, 25, 5);
    pop();
  }

function runTest() {
    generationCount = genSlider.value();
    lifespan = lifeSlider.value();
    let popCount = popSlider.value();
    let url = "/start?genCount=" + generationCount + "&populationCount=" + popCount + "&lifespan=" + lifespan;
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange=(e)=> {
        console.log(Http);
        let response = JSON.parse(Http.responseText)
        positionData = response.allRocketPositions;
        runAlgo = true;
    }
}