<script>
  import { page } from "$app/stores";
  import axios from "axios";
  import { onMount } from "svelte";
  import { jwt_token } from "../../../store";
  import { isAuthenticated, user as storeUser } from "../../../store";

  const api_root = $page.url.origin;

  let bookings = []; // Array für die Rentals
  let defaultPageSize = 3; // Standard-Größe für die Pagination
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

  // Variablen für die Motorräder
  let motorcycleDetails = {};

  // Variablen für die Vermieter
  let ownerDetails = {};

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

        bookings.forEach((booking) => {
          if (!motorcycleDetails[booking.motorcycleId]) {
            getMotorcycleDetails(booking.motorcycleId);
          }
        });
        bookings.forEach((booking) => {
          if (!ownerDetails[booking.ownerId]) {
            getOwnerDetails(booking.ownerId);
          }
        });
      })
      .catch(function (error) {
        alert("Could not get your bookings");
        console.log(error);
      });
  }

  // Funktion um die Motorrad Details zu holen
  function getMotorcycleDetails(motorcycleId) {
    var config = {
      method: "get",
      url: api_root + "/api/motorcycles/" + motorcycleId,
      headers: { Authorization: "Bearer " + $jwt_token },
    };

    axios(config)
      .then(function (response) {
        motorcycleDetails[motorcycleId] = response.data;
      })
      .catch(function (error) {
        alert("Could not get motorcycle details");
        console.log(error);
      });
  }

  // Funktion um die Owner Details zu holen
  function getOwnerDetails(ownerId) {
    var config = {
      method: "get",
      url: api_root + "/api/users/" + ownerId,
      headers: { Authorization: "Bearer " + $jwt_token },
    };

    axios(config)
      .then(function (response) {
        ownerDetails[ownerId] = response.data;
      })
      .catch(function (error) {
        alert("Could not get owner details");
        console.log(error);
      });
  }

</script>

<!-- Meine Buchungen -->
<h1 class="section-title">Meine Buchungen</h1>

<div class="row row-cols-1 row-cols-md-3 g-4">
  {#each bookings as booking}
    <div class="col">
      <div class="card h-100 shadow-sm p-3">
        <div class="card-body">
          <div class="booking-details">
            <div class="detail-row">
              <span class="label">Motorrad:</span>
              <span class="value">
                {#if motorcycleDetails[booking.motorcycleId]}
                  {motorcycleDetails[booking.motorcycleId].brand}
                  {motorcycleDetails[booking.motorcycleId].model},
                  {motorcycleDetails[booking.motorcycleId].year}
                {:else}
                  Laden...
                {/if}
              </span>
            </div>
            <div class="detail-row">
              <span class="label">Startdatum:</span>
              <span class="value">{booking.startingTime}</span>
            </div>
            <div class="detail-row">
              <span class="label">Enddatum:</span>
              <span class="value">{booking.endingTime}</span>
            </div>
            <div class="detail-row">
              <span class="label">Preis:</span>
              <span class="value">{booking.price} CHF</span>
            </div>
            {#if booking.status !== "AVAILABLE"}
              <div class="detail-row">
                <span class="label">Vermieter:</span>
                <span class="value">
                  {#if ownerDetails[booking.ownerId]}
                    {ownerDetails[booking.ownerId].firstName}
                    {ownerDetails[booking.ownerId].lastName}
                  {:else}
                    Laden...
                  {/if}
                </span>
              </div>
              <div class="detail-row">
                <span class="label">Kontakt:</span>
                <span class="value">
                  {#if ownerDetails[booking.ownerId]}
                    {ownerDetails[booking.ownerId].phoneNumber}<br />
                    {ownerDetails[booking.ownerId].email}
                  {:else}
                    Laden...
                  {/if}
                </span>
              </div>
            {/if}
            <div class="detail-row">
              <span class="label">Abhol-/Rückgabeort:</span>
              <span class="value">
                {booking.pickupAdress.city}
                {booking.pickupAdress.postalCode}, {booking.pickupAdress.country}<br />
                {booking.pickupAdress.street}, {booking.pickupAdress.streetNumber}
              </span>
            </div>
            <div class="detail-row">
              <span class="label">Status:</span>
              <span class="value">
                {#if booking.status === "AVAILABLE"}
                  <span class="badge bg-success">Verfügbar</span>
                {:else if booking.status === "BOOKED"}
                  <span class="badge bg-warning text-dark">Gebucht</span>
                {:else if booking.status === "COMPLETED"}
                  <span class="badge bg-secondary">Abgeschlossen</span>
                {:else}
                  <span class="badge bg-dark">Unbekannt</span>
                {/if}
              </span>
            </div>
          </div>
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

  .booking-details {
    display: flex;
    flex-direction: column;
    gap: 10px; /* Abstand zwischen Zeilen */
  }

  .detail-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 8px; /* Abstand zwischen Zeilen */
  }

  .label {
    font-weight: bold;
    width: 150px; /* Einheitliche Breite für Labels */
    text-align: right;
    padding-right: 10px;
  }

  .value {
    text-align: left;
    flex: 1;
  }

  .card {
    max-width: 400px; /* Maximale Breite für jede Card */
    background-color: #f8f9fa;
    border: none;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  .btn-outline-primary {
    border-color: #007bff;
    color: #007bff;
  }

  .btn-outline-primary:hover {
    background-color: #007bff;
    color: #fff;
  }

  .badge {
    font-size: 0.9rem;
    padding: 5px 10px;
    border-radius: 10px;
  }


</style>

