const Http = new XMLHttpRequest();
let positionData;
let runAlgo = false;
let generationCount = 10;
let currentGeneration = 0;
let lifespan = 200;
let currentLife = 0;

function setup() {
    createCanvas(800, 800);
    let url = "/start";
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange=(e)=> {
        let response = JSON.parse(Http.responseText)
        this.positionData = response.allRocketPositions;

        this.runAlgo = true;
    }
}

function draw() {
    background(0);
    if(this.runAlgo) {
        for(let rocket of this.positionData) {
            show(rocket[currentGeneration][currentLife])
        }
        currentLife++;
        if(currentLife >= lifespan) {
            console.log("Finished for this generation")
            currentLife = 0;
            currentGeneration++;
        }

        if(currentGeneration >= generationCount) {
            console.log("Finshed all generations")
            currentGeneration = 0;
            this.runAlgo = false
        }
    }
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