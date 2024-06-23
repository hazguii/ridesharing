<script>
    import { enhance } from '$app/forms';
    import { AspectRatio, Button, ButtonSet, Column, Grid, Row, Tile } from "carbon-components-svelte";
	import { ArrowRight, Login, Share, TaskRemove, TaskView } from "carbon-icons-svelte";
	import DateTime from "$lib/components/DateTime.svelte";
    export let rsOffer;
   
</script>

            <Tile>
                <div>            
                    <DateTime datetime={rsOffer.departureDateTime}/>
                </div>
                <div>{rsOffer.departureAddress.city} <ArrowRight/> {rsOffer.destinationAddress.city}</div>
                    {#if !rsOffer.cancelled}
                    <div>
                        <form method="POST" use:enhance>
                            <input type="text" name="rsOfferId" value={rsOffer.rsOfferId.value} hidden />
                                <Button href="/rsOffers/{rsOffer.rsOfferId.value}"  kind="ghost" icon={TaskView} iconDescription="See offer details" />
                                {#if !rsOffer.published}
                                    <Button type="submit" formaction="?/publish" kind="ghost" icon={Share} iconDescription="Publish offer" />
                                {/if}
                                <Button type="submit" formaction="?/cancel" kind="ghost" icon={TaskRemove} iconDescription="Cancel offer" />
                        </form>
                    </div>
                    {/if}
            </Tile>


