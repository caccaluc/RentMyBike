<script>
  import { onMount } from "svelte";
  import { page } from "$app/stores";
  import axios from "axios";

  // Die ID wird aus der URL extrahiert
  export let params;
  let motorcycleId = params.id;

  let motorcycle = {};

  const api_root = $page.url.origin;

  onMount(() => {
    getMotorcycleDetails();
  });

  function getMotorcycleDetails() {
    var config = {
      method: "get",
      url: `${api_root}/api/motorcycles/${motorcycleId}`,
      headers: {},
    };

    axios(config)
      .then((response) => {
        motorcycle = response.data;
      })
      .catch((error) => {
        console.error("Could not get motorcycle details:", error);
      });
  }
</script>
  
  <h1>Motorrad Details</h1>
  {#if motorcycle}
    <div class="motorcycle-details">
      <p><strong>Marke:</strong> {motorcycle.brand}</p>
      <p><strong>Modell:</strong> {motorcycle.model}</p>
      <p><strong>Jahr:</strong> {motorcycle.year}</p>
      <p><strong>PS:</strong> {motorcycle.ps}</p>
      <p><strong>Kilometerstand:</strong> {motorcycle.km}</p>
      <p><strong>Farbe:</strong> {motorcycle.color}</p>
      <p><strong>Wert:</strong> {motorcycle.value} CHF</p>
    </div>
  {:else}
    <p>Lade Motorrad-Informationen...</p>
  {/if}
  
  <style>
    .motorcycle-details {
      border: 1px solid #e0e0e0;
      padding: 20px;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }
  </style>
   