
<script>
    import { page } from "$app/stores";
    import axios from "axios";
    import { onMount } from "svelte";
    import { jwt_token } from "../../../store";
    import { isAuthenticated, user as storeUser } from "../../../store";
  
    const api_root = $page.url.origin;
  
    let bookings = []; // Array für die Rentals
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
        getMyBookings();
      }
    }
  

    function getMyBookings() {
      let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;
    var config = {
      method: "get",
      url: api_root + "/api/me/bookings" + query,
      headers: { Authorization: "Bearer " + $jwt_token },
    };
  
    axios(config)
      .then(function (response) {
        bookings = response.data.content; // Speichern der Buchung
        nrOfPages = response.data.totalPages; // Anzahl der Seiten
        console.log("Buchungen geladen:", bookings); // Debug-Log
      })
      .catch(function (error) {
        alert("Could not get your bookings");
        console.log(error);
      });
  }
</script>  

 <!-- Meine Buchung -->
<h1 class="section-title">Meine Buchungen</h1>
<div class="row row-cols-1 row-cols-md-2 g-4">
    {#each bookings as booking}
        <div class="col">
            <div class="card h-100 shadow-sm">
                <div class="card-body">
                    <p><strong>Motorrad-Id:</strong> {booking.motorcycleId}</p>
                    <p><strong>Startdatum:</strong> {booking.startingTime}</p>
                    <p><strong>Enddatum:</strong> {booking.endingTime}</p>
                    <p><strong>Preis:</strong> {booking.price} CHF</p>
                    {#if booking.status !== "AVAILABLE"}
                        <p><strong>Vermieter:</strong> {booking.ownerId}</p>
                    {/if}

                    <p><strong>Abhol-/Rückgabeort:</strong>  {booking.pickupAdress.city} {booking.pickupAdress.postalCode}, {booking.pickupAdress.country}<br>
                            {booking.pickupAdress.street}, {booking.pickupAdress.streetnumber}</p>
                    
                    <p>
                        <strong>Status:</strong>
                        {#if booking.status === "AVAILABLE"}
                            <span class="badge bg-success">Verfügbar</span>
                        {:else if booking.status === "BOOKED"}
                            <span class="badge bg-warning text-dark">Gebucht</span>
                        {:else if booking.status === "COMPLETED"}
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
        <a class="page-link" href={"/rides/mybookedRides?page=" + (i + 1)}>{i + 1}</a>
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