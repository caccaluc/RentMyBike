<script>
    import axios from "axios";
    import { page } from "$app/stores";
    import { onMount } from "svelte";
  
    const api_root = $page.url.origin;
  
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
  
    onMount(() => {
      getUsers();
    });
  
    function getUsers() {
      var config = {
        method: "get",
        url: api_root + "/api/users/all",
        headers: {},
      };
  
      axios(config)
        .then(function (response) {
          users = response.data;
        })
        .catch(function (error) {
          alert("Could not get users");
          console.log(error);
        });
    }
  
  </script>


<h1>Alle Benutzer</h1>
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
            </tr>
        {/each}
    </tbody>
</table>