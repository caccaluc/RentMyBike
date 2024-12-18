<script>
  import { page } from "$app/stores";
  import axios from "axios";
  import { onMount } from "svelte";
  import { jwt_token } from "../../store";
  import { isAuthenticated, user as storeUser } from "../../store";

  const api_root = $page.url.origin;

  let rentals = []; // Array für die Vermietungen
  let bookings = []; // Array für die Buchungen
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

  // Wenn die Seite geladen wird, wird die Funktion getUser() aufgerufen
  onMount(() => {
    if ($jwt_token !== "") {
      getUser();
    }
  });

  // Funktion, um die Daten des eingeloggten Users zu bekommen
  function getUser() {
    var config = {
      method: "get",
      url: api_root + "/api/me/user",
      headers: { Authorization: "Bearer " + $jwt_token },
    };

    axios(config)
      .then(function (response) {
        console.log("User data:", response.data);
        user = response.data;
      })
      .catch(function (error) {
        console.error("Could not get user details:", error);
        throw error;
      });
  }

  // Funktion um die Buchungen des eingeloggten Users zu bekommen
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

<!-- Hauptüberschrift -->
<h1 class="text-primary text-center my-4">Mein Profil</h1>

<!-- Benutzerprofil -->
{#if $isAuthenticated}
  <h2 class="section-title">Persönliche Daten</h2>
  <div class="card card-container shadow-sm">
    <div class="card-header text-center bg-light">
      <img src={$storeUser.picture} alt="Profilbild" />
      <h3 class="mt-2 mb-0">
        {$storeUser.given_name}
        {$storeUser.family_name}
      </h3>
      <small class="text-muted">{$storeUser.email}</small>
    </div>
    <div class="card-body">
      <div class="info-row">
        <b>Führerscheinnummer:</b>
        {user.licenceCode || "Nicht verfügbar"}
      </div>
      <div class="info-row">
        <b>Geburtsdatum:</b>
        {user.birthdate || "Nicht verfügbar"}
      </div>
      <div class="info-row">
        <b>Telefonnummer:</b>
        {user.phoneNumber || "Nicht verfügbar"}
      </div>
      <div class="info-row">
        <b>Proflistatus:</b>
        {user.state || "Nicht verfügbar"}
      </div>
      {#if $storeUser.user_roles && $storeUser.user_roles.length > 0}
        <div class="info-row">
          <b>Rollen:</b>
          {$storeUser.user_roles.join(", ")}
        </div>
      {/if}
    </div>
  </div>
{/if}

<!-- Adresse -->
<h2 class="section-title">Adresse</h2>
<div class="card card-container shadow-sm">
  <div class="card-body">
    <div class="info-row">
      <b>Strasse:</b>
      {user.adress?.street || "Nicht verfügbar"}
    </div>
    <div class="info-row">
      <b>Hausnummer:</b>
      {user.adress?.streetNumber || "Nicht verfügbar"}
    </div>
    <div class="info-row">
      <b>PLZ:</b>
      {user.adress?.postalCode || "Nicht verfügbar"}
    </div>
    <div class="info-row">
      <b>Ort:</b>
      {user.adress?.city || "Nicht verfügbar"}
    </div>
    <div class="info-row">
      <b>Land:</b>
      {user.adress?.country || "Nicht verfügbar"}
    </div>
  </div>
</div>

<!-- Bewertungen -->
<h2 class="section-title">Bewertungen</h2>
<div class="card card-container shadow-sm">
  <div class="card-body">
    {#if user.ratings && user.ratings.length > 0}
      <div class="info-row">
        <b>Durchschnittliche Bewertung:</b>
        {user.averageRating || "Keine Daten"}
      </div>
      <div class="info-row">
        <b>Anzahl der Bewertungen:</b>
        {user.numberOfRatings || 0}
      </div>
      <ul class="list-group mt-3">
        {#each user.ratings as rating}
          <li class="list-group-item">
            <b>Bewertung:</b>
            {rating.rating} ★ - <i>"{rating.comment || "Kein Kommentar"}"</i>
          </li>
        {/each}
      </ul>
    {:else}
      <p class="empty-message">Keine Bewertungen vorhanden</p>
    {/if}
  </div>
</div>

<!-- Tabelle Vermietungen -->
<h2 class="section-title">Meine Vermietungen</h2>

<table class="table table-striped table-bordered">
  <thead class="table-dark">
    <tr>
      <th scope="col">Motorrad ID</th>
      <th scope="col">Startdatum</th>
      <th scope="col">Enddatum</th>
      <th scope="col">Preis (CHF)</th>
      <th scope="col">Status</th>
    </tr>
  </thead>
  <tbody>
    {#each rentals as rental}
      <tr>
        <td>{rental.motorcycleId}</td>
        <td>{rental.startingTime}</td>
        <td>{rental.endingTime}</td>
        <td>{rental.price}</td>
        <td>
          {#if rental.status === "AVAILABLE"}
            <span class="badge bg-success">Verfügbar</span>
          {:else if rental.status === "BOOKED"}
            <span class="badge bg-warning">Gebucht</span>
          {:else if rental.status === "COMPLETED"}
            <span class="badge bg-secondary">Abgeschlossen</span>
          {:else}
            <span class="badge bg-dark">Unbekannt</span>
          {/if}
        </td>
      </tr>
    {/each}
  </tbody>
</table>

<!-- Tabelle Buchungen -->
<h2 class="section-title">Meine Buchungen</h2>

<table class="table table-striped table-bordered">
  <thead class="table-dark">
    <tr>
      <th scope="col">Motorrad ID</th>
      <th scope="col">Startdatum</th>
      <th scope="col">Enddatum</th>
      <th scope="col">Preis (CHF)</th>
      <th scope="col">Status</th>
    </tr>
  </thead>
  <tbody>
    {#each bookings as booking}
      <tr>
        <td>{booking.motorcycleId}</td>
        <td>{booking.startingTime}</td>
        <td>{booking.endingTime}</td>
        <td>{booking.price}</td>
        <td>
          {#if booking.status === "AVAILABLE"}
            <span class="badge bg-success">Verfügbar</span>
          {:else if booking.status === "BOOKED"}
            <span class="badge bg-warning">Gebucht</span>
          {:else if booking.status === "COMPLETED"}
            <span class="badge bg-secondary">Abgeschlossen</span>
          {:else}
            <span class="badge bg-dark">Unbekannt</span>
          {/if}
        </td>
      </tr>
    {/each}
  </tbody>
</table>

<style>
  .card-container {
    max-width: 700px;
    margin: 20px auto;
  }

  .card-header img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    object-fit: cover;
  }

  .section-title {
    font-size: 1.5rem;
    margin-top: 30px;
    border-bottom: 2px solid #007bff;
    padding-bottom: 10px;
  }

  .info-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  .list-group-item {
    background: #f8f9fa;
    border: none;
    border-bottom: 1px solid #ddd;
  }

  .empty-message {
    color: gray;
    font-style: italic;
    text-align: center;
  }
</style>
