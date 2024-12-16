<script>
  import axios from "axios";
  import { page } from "$app/stores";
  import { jwt_token, user as authUser } from "../../../store"; // JWT und Auth-User importieren

  const api_root = $page.url.origin;

  let user = {
      firstName: $authUser.given_name || "", // Vorname aus JWT
      lastName: $authUser.family_name || "", // Nachname aus JWT
      email: $authUser.email || "", // E-Mail aus JWT
      licenseCode: null,
      phoneNumber: null,
      adress: {
          street: null,
          streetNumber: null,
          postalCode: null,
          city: null,
          country: null,
      },
      birthdate: null,
  };

  function createUser() {
      var config = {
          method: "post",
          url: api_root + "/api/users/create",
          headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + $jwt_token,
          },
          data: user,
      };

      axios(config)
          .then(function (response) {
              alert("User wurde erfolgreich erstellt!");
              console.log(response.data);
          })
          .catch(function (error) {
              alert("Fehler beim Erstellen des Users.");
              console.error(error);
          });
  }
</script>


<h1 class="mt-3">Benutzerdaten vervollständigen</h1>

<form class="mb-5">
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="firstName">Vorname</label>
          <input
              class="form-control"
              id="firstName"
              type="text"
              bind:value={user.firstName}
              readonly
          />
      </div>
  </div>
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="lastName">Nachname</label>
          <input
              class="form-control"
              id="lastName"
              type="text"
              bind:value={user.lastName}
              readonly
          />
      </div>
  </div>
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="email">E-Mail</label>
          <input
              class="form-control"
              id="email"
              type="email"
              bind:value={user.email}
              readonly
          />
      </div>
  </div>

  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="licenseCode">Führerschein-ID</label>
          <input
              bind:value={user.licenseCode}
              class="form-control"
              id="licenseCode"
              type="text"
          />
      </div>
  </div>
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="phoneNumber">Telefonnummer</label>
          <input
              bind:value={user.phoneNumber}
              class="form-control"
              id="phoneNumber"
              type="text"
          />
      </div>
  </div>
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="birthdate">Geburtsdatum</label>
          <input
              bind:value={user.birthdate}
              class="form-control"
              id="birthdate"
              type="date"
          />
      </div>
  </div>

  <h3>Adresse</h3>
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="street">Strasse</label>
          <input
              bind:value={user.adress.street}
              class="form-control"
              id="street"
              type="text"
          />
      </div>
  </div>
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="streetNumber">Hausnummer</label>
          <input
              bind:value={user.adress.streetNumber}
              class="form-control"
              id="streetNumber"
              type="text"
          />
      </div>
  </div>
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="postalCode">PLZ</label>
          <input
              bind:value={user.adress.postalCode}
              class="form-control"
              id="postalCode"
              type="text"
          />
      </div>
  </div>
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="city">Stadt</label>
          <input
              bind:value={user.adress.city}
              class="form-control"
              id="city"
              type="text"
          />
      </div>
  </div>
  <div class="row mb-3">
      <div class="col">
          <label class="form-label" for="country">Land</label>
          <input
              bind:value={user.adress.country}
              class="form-control"
              id="country"
              type="text"
          />
      </div>
  </div>

  <button type="button" class="btn btn-primary" on:click={createUser}>
      Daten speichern
  </button>
</form>
