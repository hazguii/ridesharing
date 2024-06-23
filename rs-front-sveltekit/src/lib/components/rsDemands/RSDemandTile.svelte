<script>
    import {enhance} from "$app/forms";
    import { AspectRatio, Button, Column, Grid,  Row, Tile } from "carbon-components-svelte";
	import { ArrowRight, Share, TaskRemove, TaskView } from "carbon-icons-svelte";
	import DateTime from "$lib/components/DateTime.svelte";
    export let rsDemand;

</script>

            <Tile>
            <div><DateTime datetime={rsDemand.departureDateTime}/></div>
            <div>{rsDemand.departureAddress.city} <ArrowRight/> {rsDemand.destinationAddress.city}</div>
            {#if !rsDemand.cancelled}
            <div>
            <form method="POST" use:enhance>
                <input type="text" name="rsDemandId" value="{rsDemand.rsDemandId.value}" hidden/>
                <Button href="/rsDemands/{rsDemand.rsDemandId.value}"  kind="ghost" icon={TaskView} iconDescription="See demand details" />
                {#if !rsDemand.published}
                        <Button type="submit" formaction="?/publish"  kind="ghost" icon={Share} iconDescription="Publish demand" />
                {/if}
                <Button type="submit" formaction="?/cancel" kind="ghost" icon={TaskRemove} iconDescription="Cancel demand" />
            </form> 
            </div>
            {/if}
        </Tile>