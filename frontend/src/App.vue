<template>
  <v-app>
    <v-toolbar tabs>
      <v-toolbar-side-icon></v-toolbar-side-icon>
      <v-toolbar-title>Page title</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>search</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>more_vert</v-icon>
      </v-btn>
      <v-tabs slot="extension" v-model="tabs" fixed-tabs color="transparent">
        <v-tabs-slider></v-tabs-slider>
        <v-tab href="#leaderboard" class="primary--text">
          <v-icon>phone</v-icon>
        </v-tab>
        <v-tab href="#testPage" class="primary--text">
          <v-icon>favorite</v-icon>
        </v-tab>
      </v-tabs>
    </v-toolbar>
    <v-tabs-items v-model="tabs" class="white elevation-1">
      <v-tab-item value="leaderboard">
        <leaderboard/>
      </v-tab-item>
      <v-tab-item value="testPage">
        <testPage/>
      </v-tab-item>
    </v-tabs-items>
  </v-app>
</template>

<script>
import HelloWorld from "./components/HelloWorld";
import leaderboard from "./components/Leaderboard";
import testPage from "./components/TestPage";

export default {
  name: "App",
  components: {
    HelloWorld,
    leaderboard,
    testPage
  },
  data() {
    return {
      tabs: null
    };
  }
};
</script>
