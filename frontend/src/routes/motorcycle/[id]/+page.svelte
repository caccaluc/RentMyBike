<script>
  import { onMount } from "svelte";
  import { page } from "$app/stores";
  import axios from "axios";

  // Die ID wird aus der URL extrahiert
  export let params;
  console.log('Aktuelle params:', params);

  //Variables
  let motorcycle = {
    id: null,
    brand: null,
    model: null,
    year: null,
    color: null,
    licensePlate: null,
    value: null,
    ps: null,
    km: null,
    userId: null,
  };

  let motorcycle_id = null;

  // Reactive Statements
  $: if (params && params.id) {
    motorcycle_id = params.id;
    console.log("API-Wurzel:", api_root);
    console.log("Motorrad ID:", motorcycle_id);
  }

  const api_root = window.location.origin;

  onMount(() => {
    if (motorcycle_id) {
      getMotorcycleDetails()
        .then(() => console.log("Motorrad Details erfolgreich geladen"))
        .catch((error) =>
          console.error("Fehler beim Laden der Motorrad Details", error),
        );
    } else {
      console.error("Keine Motorrad-ID verfügbar.");
    }
  });

  function getMotorcycleDetails() {
    var config = {
      method: "get",
      url: `${api_root}/api/motorcycles/${motorcycle_id}`,

      headers: {},
    };

    return axios(config) // Stelle sicher, dass die Promise zurückgegeben wird
      .then((response) => {
        motorcycle = response.data;
      })
      .catch((error) => {
        console.error("Could not get motorcycle details:", error);
        throw error; // Fehler weiterleiten, damit der catch im onMount greift
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
