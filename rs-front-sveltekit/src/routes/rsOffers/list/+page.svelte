<script>
    import { AspectRatio, Column, Grid, Row } from "carbon-components-svelte";
	import RsOfferTile from '$lib/components/rsOffers/RSOfferTile.svelte';
	import { Loading } from "carbon-components-svelte";

    export let data;

</script>
{#await data.rsOffers}
<Loading />
{:then rsOffers}
<Grid padding>
    <AspectRatio ratio="1x1">
        <Row><Column><h1>Draft Offers</h1></Column></Row>
        <Row>
            {#each rsOffers.filter(o=>o.cancelled==false && o.published==false) as rsOffer}
                <Column sm={2}>
                    <RsOfferTile {rsOffer}/>
                </Column>
            {/each}
        </Row>
        <Row><Column><h1>Active Offers</h1></Column></Row>
        <Row>
            {#each rsOffers.filter(o=>o.cancelled==false && o.published==true) as rsOffer}
                <Column sm={2}>
                    <RsOfferTile {rsOffer}/>
                </Column>
            {/each}
        </Row>
        <Row><Column><h1>Cancelled Offers</h1></Column></Row>
        <Row>
            {#each rsOffers.filter(o=>o.cancelled==true) as rsOffer}
                <Column sm={2}>
                    <RsOfferTile {rsOffer}/>
                </Column>
            {/each}
        </Row>
    </AspectRatio>
</Grid>
{/await}
