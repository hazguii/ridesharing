<script>
    import { DataTable,Link } from "carbon-components-svelte";
    export let rsDemands=[];

    //add id column eachtime data changes (required for the datatable component)
    $:rsDemands.forEach((node) => node.id = node.rsDemandId.value);

    let headers = [
    { key: "departureAddress.city", value: "dep city" },
    { key: "destinationAddress.city", value: "dest city" },
    { key: "departureDateTime", value: "time" },
    { key: "rsDemandId.value", value: "details" }
  ]
</script>

<DataTable bind:rows={rsDemands} headers={headers}>
    <svelte:fragment slot="cell" let:row let:cell>
        {#if cell.key === "rsDemandId.value"}
          <Link href="/rsDemands/{cell.value}">voir</Link>
        {:else}
            {cell.value}
        {/if}
    </svelte:fragment>
</DataTable>


<!-- 
<table border='1'>
    <tr>
        <th>rsDemandId</th>
        <th>user</th>
        <th>departure</th>
        <th>destination</th>
        <th>time</th>
        <th>details</th>
    </tr>
{#each rsDemands as rsDemand}
    <tr>
        <td>{rsDemand.rsDemandId.value}</td>
        <td>{rsDemand.userId.id}</td>
        <td>{rsDemand.departureAddress.city}</td>
        <td>{rsDemand.destinationAddress.city}</td>
        <td>{rsDemand.departureDateTime}</td>
        <td><a href='rsDemands/{rsDemand.rsDemandId.value}'>voir</a></td>
    </tr>
{/each}
</table>
 -->
