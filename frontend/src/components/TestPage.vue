/* eslint-disable */
<template>
  <div style="margin-top: 72px;">
    <v-container>
      <div ref="canvas"></div>
      <v-btn @click="runTest">Run Test</v-btn>
      <v-slider v-model="generationCount" thumb-label="always" max="30"></v-slider>
      <v-slider v-model="lifespan" thumb-label="always" max="1000"></v-slider>
      <v-slider v-model="popCount" thumb-label="always" max="50"></v-slider>
    </v-container>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data: function() {
    return {
      script: null,
      ps: null,
      canvas: null,
      runAlgorithm: false,
      currentLife: 0,
      positionData: null,
      generationCount: 10,
      lifespan: 200,
      popCount: 15,
      currentGeneration: 0
    };
  },

  mounted() {
    this.script = p => {
      this.x = 100;
      this.y = 100;

      // eslint-disable-next-line
      p.setup = _ => {
        this.canvas = p.createCanvas(800, 800);
        this.canvas.parent(this.$refs.canvas);
        p.frameRate(60);
      };
      // eslint-disable-next-line
      p.draw = _ => {
        p.background(0);
        if (this.runAlgorithm) {
          for (let rocket of this.positionData[this.currentGeneration]) {
            this.show(rocket[this.currentLife]);
          }
          this.currentLife++;
          if (this.currentLife >= this.lifespan) {
            this.currentLife = 0;
            this.currentGeneration++;
          }
          if (this.currentGeneration >= this.generationCount) {
            this.currentGeneration = 0;
            this.runAlgorithm = false;
          }
        }
        p.fill(255);
        p.ellipse(400, 50, 20, 20);
        p.noFill();
        p.stroke(255)
        p.rect(300, 350, 200, 100);
      };
    };
    const P5 = require("p5");
    this.ps = new P5(this.script);
  },
  methods: {
    show: function(rocket) {
      if (rocket == null) {
        console.log("rocket is null");
      }
      this.ps.push();
      //color customization of rockets
      this.ps.noStroke();
      if (rocket.colour == "red") {
        this.ps.fill(255, 0, 0);
      } else if (rocket.colour == "green") {
        this.ps.fill(0, 255, 0);
      } else if (rocket.colour == "gold") {
        this.ps.fill(255, 255, 0);
      } else {
        this.ps.fill(255, 150);
      }
      //translate to the postion of rocket
      this.ps.translate(rocket.x, rocket.y);
      //rotatates to the angle the rocket is pointing
      this.ps.rotate(rocket.heading);
      //creates a rectangle shape for rocket
      //    rectMode(CENTER);
      //    rect(0, 0, 25, 5);
      this.ps.ellipse(0, 0, 5, 5);
      this.ps.pop();
    },
    runTest: function() {
      let url =
        "http://localhost:8080/start?genCount=" +
        this.generationCount +
        "&populationCount=" +
        this.popCount +
        "&lifespan=" +
        this.lifespan;

      axios
        .get(url)
        .then(response => {
          this.positionData = response.data.allRocketPositions;
          this.runAlgorithm = true;
          console.log(this.positionData);
        })
        .catch(e => {
          console.log(e);
        });
    }
  }
};
</script>