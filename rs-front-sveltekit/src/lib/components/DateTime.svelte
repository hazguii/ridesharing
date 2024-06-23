<script>
    export let datetime;
    let split = datetime.split('T');
    let date = split[0];
    let time = split[1];
</script>
{date} {time}
