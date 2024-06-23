<script>
	import { Loading } from "carbon-components-svelte";
    import { AspectRatio, Button, Column, Grid,  Row, Tile } from "carbon-components-svelte";
	import RsDemandTile from "$lib/components/rsDemands/RSDemandTile.svelte";
    export let data;

</script>
{#await data.rsDemands}
    <Loading />
{:then rsDemands}
<Grid padding>
    <AspectRatio ratio="1x1">
        <Row><Column><h1>Draft demands</h1></Column></Row>
        <Row>
        {#each rsDemands.filter(d=>d.cancelled==false && d.published==false) as rsDemand}
        <Column sm={2}>
            <RsDemandTile {rsDemand}/>
        </Column>
        {/each}
        </Row>
        <Row><Column><h1>Active demands</h1></Column></Row>
        <Row>
        {#each rsDemands.filter(d=>d.cancelled==false && d.published==true) as rsDemand}
        <Column sm={2}>
            <RsDemandTile {rsDemand}/>
        </Column>
        {/each}
        </Row>
        <Row><Column><h1>Cancelled demands</h1></Column></Row>
        <Row>
        {#each rsDemands.filter(d=>d.cancelled==true) as rsDemand}
        <Column sm={2}>
            <RsDemandTile {rsDemand}/>
        </Column>
        {/each}
        </Row>
</AspectRatio>
</Grid>
{:catch error}
    error {error}
{/await}
