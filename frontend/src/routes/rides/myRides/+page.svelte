<script>
    import { page } from "$app/stores";
    import axios from "axios";
    import { onMount } from "svelte";
    import { jwt_token } from "../../../store";
    import { isAuthenticated, user as storeUser } from "../../../store";
  
    const api_root = $page.url.origin;
  
    let rentals = []; // Array für die Rentals
    let defaultPageSize = 6; // Standard-Größe für die Pagination
    let nrOfPages = 0; // Anzahl der Seiten für Pagination
    let currentPage; // Standard-Sei
  
    // Variablen für den eingeloggten User
    let user = {
      id: null,
      state: null,
      firstName: null,
      lastName: null,
      email: null,
      licenceCode: null,
      birthdate: null,
      phoneNumber: null,
      averageRating: null,
      numberOfRatings: null,
      adress: null,
      ratings: null,
      };
  
      $: {
      if ($jwt_token !== "") {
        let searchParams = $page.url.searchParams;
  
        if (searchParams.has("page")) {
          currentPage = searchParams.get("page");
        } else {
          currentPage = "1";
        }
        getMyRentals();
      }
    }
  
    /* 
      onMount(() => {
        getRides();
      });*/
  
    // Funktion, um die Rides zu bekommen
    function getMyRentals() {
      let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;
    var config = {
      method: "get",
      url: api_root + "/api/me/rentals" + query,
      headers: { Authorization: "Bearer " + $jwt_token },
    };
  
    axios(config)
      .then(function (response) {
        rentals = response.data.content; // Speichern der Vermietungen
        nrOfPages = response.data.totalPages; // Anzahl der Seiten
        console.log("Vermietungen geladen:", rentals); // Debug-Log
      })
      .catch(function (error) {
        alert("Could not get your rentals");
        console.log(error);
      });
  }
</script>  

 <!-- Tabelle Vermietungen -->
 <!-- Meine Vermietungen -->
<h2 class="section-title">Meine Vermietungen</h2>

<div class="row row-cols-1 row-cols-md-2 g-4">
  {#each rentals as rental}
    <div class="col">
      <div class="card h-100 shadow-sm">
        <div class="card-header bg-primary text-white">
          <h5 class="card-title mb-0">Motorrad ID: {rental.motorcycleId}</h5>
        </div>
        <div class="card-body">
          <p><strong>Startdatum:</strong> {rental.startingTime}</p>
          <p><strong>Enddatum:</strong> {rental.endingTime}</p>
          <p><strong>Preis:</strong> {rental.price} CHF</p>
          <p>
            <strong>Status:</strong>
            {#if rental.status === "AVAILABLE"}
              <span class="badge bg-success">Verfügbar</span>
            {:else if rental.status === "BOOKED"}
              <span class="badge bg-warning text-dark">Gebucht</span>
            {:else if rental.status === "COMPLETED"}
              <span class="badge bg-secondary">Abgeschlossen</span>
            {:else}
              <span class="badge bg-dark">Unbekannt</span>
            {/if}
          </p>
        </div>
        <div class="card-footer bg-light text-end">
          <button class="btn btn-outline-primary btn-sm">Details anzeigen</button>
        </div>
      </div>
    </div>
  {/each}
</div>

<!-- Pagination -->
<nav class="mt-4">
  <ul class="pagination justify-content-center">
    {#each Array(nrOfPages) as _, i}
      <li class="page-item {currentPage == i + 1 ? 'active' : ''}">
        <a class="page-link" href={"/rides/myRides?page=" + (i + 1)}>{i + 1}</a>
      </li>
    {/each}
  </ul>
</nav>


<style>
  .section-title {
    font-size: 1.5rem;
    margin-top: 30px;
    border-bottom: 2px solid #007bff;
    padding-bottom: 10px;
  }
</style>