<script>
    import { signIn, signOut } from '@auth/sveltekit/client';
	import { page } from '$app/stores';
//console.log($page.data.session);
</script>


{#if $page.data.session}
<h1>Welcome to RS</h1>
<span class="signedInText">
  <small>Signed in as</small><br />
  <strong>{$page.data.session.user.email || $page.data.session.user.name}</strong><br/>
  <small>Id</small>: <strong>{$page.data.session.user.id}</strong><br/>
  <small>Role</small>: <strong>{$page.data.session.user.roles}</strong>
</span>
<p>Session expiry: {$page.data.session?.expires}</p>
<button on:click={() => signOut()} class="button">Sign out button</button>
{:else}
<h1>Access Denied</h1>
<p>
  <button on:click={() => signIn('keycloak')}>Sign In button</button>
</p>
{/if}



