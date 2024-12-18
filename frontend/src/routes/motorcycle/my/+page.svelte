<script>
    import { page } from "$app/stores";
    import axios from "axios";
    import { onMount } from "svelte";
    import { jwt_token } from "../../../store";
  
    const api_root = $page.url.origin;
  
 
    /** Definition für Motorräder */
  
    let motorcycles = [];
    let motorcycle = {
      brand: null,
      model: null,
      year: null,
      color: null,
      licenceplate: null,
      value: null,
      ps: null,
      km: null,
      UserId: null,
      id: null,
    };
  
    onMount(() => {
      getMotorcycles();
    });
  
    function getMotorcycles() {
      var config = {
        method: "get",
        url: api_root + "/api/me/motorcycles",
        headers: { Authorization: "Bearer " + $jwt_token },
      };
  
      axios(config)
        .then(function (response) {
          motorcycles = response.data;
        })
        .catch(function (error) {
          alert("Could not get motorcycles");
          console.log(error);
        });
    }
  
    function deleteMotorcycle(motorcycleId) {
      var config = {
        method: "delete",
        url: api_root + `/api/motorcycles/${motorcycleId}`,
        headers: { Authorization: "Bearer " + $jwt_token },
      };
  
      axios(config)
        .then(function (response) {
          alert("Motorrad erfolgreich gelöscht");
          getMotorcycles(); // Liste der Motorräder aktualisieren
        })
        .catch(function (error) {
          alert("Fehler beim Löschen des Motorrads");
          console.error("Error:", error);
        });
    }
  </script>
  
  
  <h1 class="section-title">Meine Motorräder</h1>
  
  <table class="table">
    <thead>
      <tr>
        <th scope="col">Marke</th>
        <th scope="col">Modell</th>
        <th scope="col">Jahr</th>
        <th scope="col">PS</th>
        <th scope="col">Kilometerstand</th>
        <th scope="col">Farbe</th>
        <th scope="col">Wert</th>
        <th scope="col">Aktionen</th>
      </tr>
    </thead>
    <tbody>
      {#each motorcycles as motorcycle}
        <tr>
          <td>{motorcycle.brand}</td>
          <td>{motorcycle.model}</td>
          <td>{motorcycle.year}</td>
          <td>{motorcycle.ps}</td>
          <td>{motorcycle.km}</td>
          <td>{motorcycle.color}</td>
          <td>{motorcycle.value}</td>
          <td>
            <button
              class="btn btn-danger"
              on:click={() => deleteMotorcycle(motorcycle.id)}
            >
              Löschen
            </button>
        </tr>
      {/each}
    </tbody>
  </table>
  
  <section class="cards mt-5">
    <div class="card text-center shadow-lg p-4 rounded">
      <h2 class="card-title mb-3">Motorrad hinzufügen</h2>
      <p class="card-text">
        Füge eine neue Vermietung hinzu und profitiere von unserem umfassenden Service.
      </p>
      <a
        class="btn btn-primary btn-lg mt-2"
        href="/motorcycle/createMotorcycle"
        >Motorrad hinzufügen</a
      >
    </div>
  </section>
  
  <style>
    .cards {
      display: flex;
      justify-content: center;
      align-items: center;
    }
  
    .card {
      max-width: 400px;
      background-color: #f8f9fa; /* Hellgrauer Hintergrund */
      border: none; /* Entfernt Standardrahmen */
      border-radius: 10px; /* Abgerundete Ecken */
    }
  
    .card-title {
      color: #007bff; /* Blau für Titel */
      font-weight: bold;
    }
  
    .card-text {
      font-size: 1rem;
      color: #555; /* Dunkelgraue Schrift */
    }
  
    .btn-primary {
      background-color: #007bff;
      border-color: #007bff;
      transition: background-color 0.3s ease-in-out, transform 0.2s;
    }
  
    .btn-primary:hover {
      background-color: #0056b3;
      transform: scale(1.05); /* Leichtes Vergrößern beim Hover */
    }
  
    .shadow-lg {
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15); /* Schöner Schatten */
    }
    .section-title {
    font-size: 1.5rem;
    margin-top: 30px;
    border-bottom: 2px solid #007bff;
    padding-bottom: 10px;
  }
  </style>