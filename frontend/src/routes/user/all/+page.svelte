<script>
  import axios from "axios";
  import { page } from "$app/stores";
  import { onMount } from "svelte";
  import { jwt_token } from "../../../store";

  const api_root = $page.url.origin;

  // Variablen für die Filter
  let userState;

  // Variablen für die Pagination
  let currentPage;
  let nrOfPages = 0;
  let defaultPageSize = 6;

  let users = [];
  let user = {
    firstName: null,
    lastName: null,
    email: null,
    licenceCode: null,
    phoneNumber: null,
    adress: {
      street: null,
      streetNumber: null,
      postalCode: null,
      city: null,
      country: null,
    },
    birthdate: null,
    state: null,
  };

  // Wenn die Seite geladen wird, wird die Funktion getRides() aufgerufen
  $: {
    if ($jwt_token !== "") {
      let searchParams = $page.url.searchParams;

      if (searchParams.has("page")) {
        currentPage = searchParams.get("page");
      } else {
        currentPage = "1";
      }
      getUsers();
    }
  }

  function getUsers() {
    let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;

    if (userState) {
      query += "&state=" + userState;
    }

    var config = {
      method: "get",
      url: api_root + "/api/users/all" + query,
      headers: { Authorization: "Bearer " + $jwt_token },
    };

    axios(config)
      .then(function (response) {
        users = response.data.content;

        nrOfPages = response.data.totalPages;
      })
      .catch(function (error) {
        alert("Could not get users");
        console.log(error);
      });
  }

  // Funktion um Benutzer zu aktivieren
  function activateUser(userId) {
    const payload = { userId: userId };

    // Konfiguration für den PUT-Request
    const config = {
      method: "put",
      url: api_root + "/api/service/activateUser",
      headers: { Authorization: "Bearer " + $jwt_token },
      data: payload,
    };

    // API-Aufruf
    axios(config)
      .then((response) => {
        alert("Benutzer erfolgreich aktiviert!");
        getUsers(); // Benutzerliste neu laden
      })
      .catch((error) => {
        console.error("Fehler bei der Aktivierung des Benutzers:", error);
        alert("Aktivierung fehlgeschlagen!");
      });
  }

  function deactivateUser(userId) {
    const payload = { userId: userId };

    // Konfiguration für den PUT-Request
    const config = {
      method: "put",
      url: api_root + "/api/service/deactivateUser",
      headers: { Authorization: "Bearer " + $jwt_token },
      data: payload,
    };

    // API-Aufruf
    axios(config)
      .then((response) => {
        alert("Benutzer erfolgreich deaktiviert!");
        getUsers(); // Benutzerliste neu laden
      })
      .catch((error) => {
        console.error("Fehler bei der Deaktivierung des Benutzers:", error);
        alert("Deaktivierung fehlgeschlagen!");
      });
  }
</script>

<h1>Alle Benutzer</h1>

<div class="row my-3">
  <div class="col-auto">
    <label for="" class="col-form-label">User-Status: </label>
  </div>
  <div class="col-3">
    <select
      bind:value={userState}
      class="form-select"
      id="typefilter"
      type="text"
    >
      <option value=""></option>
      <option value="NEW">NEW</option>
      <option value="ACTIVE">ACTIVE</option>
      <option value="DEACTIVATED">DEACTIVATED</option>
    </select>
  </div>

  <div class="col-3">
    <a
      class="btn btn-primary"
      href={"/user/all?page=1&state=" + userState}
      role="button">Filtern</a
    >
  </div>
</div>
<table class="table">
  <thead>
    <tr>
      <th scope="col">Vorname</th>
      <th scope="col">Nachname</th>
      <th scope="col">E-Mail</th>
      <th scope="col">Führerschein-ID</th>
      <th scope="col">Telefonnummer</th>
      <th scope="col">Geburtsdatum</th>
      <th scope="col">Status</th>
      <th scope="col">ID</th>
      <th scope="col">Aktionen</th>
    </tr>
  </thead>
  <tbody>
    {#each users as user}
      <tr>
        <td>{user.firstName}</td>
        <td>{user.lastName}</td>
        <td>{user.email}</td>
        <td>{user.licenceCode}</td>
        <td>{user.phoneNumber}</td>
        <td>{user.birthdate}</td>
        <td>{user.state}</td>
        <td>{user.id}</td>
        
        <td>
          {#if user.state !== 'ACTIVE'}
            <button
            class="btn btn-success"
            on:click={() => activateUser(user.id)}
            >
            Aktivieren
            </button>
            {/if}
            {#if user.state !== 'DEACTIVATED'}
            <button
            class="btn btn-danger"
            on:click={() => deactivateUser(user.id)}
            >
            Deaktivieren
            </button>
            {/if}
        </td>
        
      </tr>
    {/each}
  </tbody>
</table>
<nav>
  <ul class="pagination">
    {#each Array(nrOfPages) as _, i}
      <li class="page-item">
        <a
          class="page-link"
          class:active={currentPage == i + 1}
          href={"/user/all?page=" + (i + 1)}
          >{i + 1}
        </a>
      </li>
    {/each}
  </ul>
</nav>
