<script>
    import { Button, Link, StructuredList, StructuredListBody, StructuredListCell, StructuredListHead, StructuredListRow } from "carbon-components-svelte";
	import { ArrowRight, TaskView } from "carbon-icons-svelte";
    export let rsDemands=[];

</script>

<StructuredList condensed>
    <StructuredListHead>
      <StructuredListRow head>
        <StructuredListCell head>Time</StructuredListCell>
        <StructuredListCell head>Trip</StructuredListCell>
        <StructuredListCell head>Actions</StructuredListCell>
      </StructuredListRow>
    </StructuredListHead>
    <StructuredListBody>
        {#each rsDemands as rsDemand}
        <StructuredListRow>
            <StructuredListCell>{rsDemand.departureDateTime}</StructuredListCell>
            <StructuredListCell>{rsDemand.departureAddress.city} <ArrowRight/> {rsDemand.destinationAddress.city}</StructuredListCell>
            <StructuredListCell><Button href="/rsDemands/{rsDemand.rsDemandId.value}"  kind="ghost" icon={TaskView} iconDescription="See offer details" /> </StructuredListCell>
        </StructuredListRow>
        {/each}
    </StructuredListBody>
</StructuredList>
