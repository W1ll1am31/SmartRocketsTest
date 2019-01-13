<template>
  <v-content fill-height>
    <v-layout justify-center>
      <v-flex lg8>
        <v-card class="elevation-12">
          <v-data-table :headers="headers" :items="results" class="elevation-1">
            <template slot="items" slot-scope="props">
              <tr>
                <td>{{ props.item.id }}</td>
                <td>{{ props.item.result }}</td>
              </tr>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
  </v-content>
</template>

<script>
export default {
  data: () => ({
    headers: [
      { text: "ID", value: "uuid" },
      { text: "Results", value: "result" }
    ],
    results: []
  })
};
</script>