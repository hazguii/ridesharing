<script>
    import { DataTable,Link } from "carbon-components-svelte";
    export let rsOffers=[];

    //add id column eachtime data changes (required for the datatable component)
    $:rsOffers.forEach((node) => node.id = node.rsOfferId.value);

    let headers = [
    { key: "departureAddress.city", value: "dep city" },
    { key: "destinationAddress.city", value: "dest city" },
    { key: "departureDateTime", value: "time" },
    { key: "rsOfferId.value", value: "details" }
  ]
</script>

<DataTable bind:rows={rsOffers} headers={headers}>
    <svelte:fragment slot="cell" let:row let:cell>
        {#if cell.key === "rsOfferId.value"}
          <Link href="/rsOffers/{cell.value}">voir</Link>
        {:else}
            {cell.value}
        {/if}
    </svelte:fragment>
</DataTable>


<!-- 
<table border='1'>
    <tr>
        <th>rsOfferId</th>
        <th>user</th>
        <th>departure</th>
        <th>destination</th>
        <th>time</th>
        <th>details</th>
    </tr>
{#each rsOffers as rsOffer}
    <tr>
        <td>{rsOffer.rsOfferId.value}</td>
        <td>{rsOffer.userId.id}</td>
        <td>{rsOffer.departureAddress.city}</td>
        <td>{rsOffer.destinationAddress.city}</td>
        <td>{rsOffer.departureDateTime}</td>
        <td><a href='rsOffers/{rsOffer.rsOfferId.value}'>voir</a></td>
    </tr>
{/each}
</table>
 -->
