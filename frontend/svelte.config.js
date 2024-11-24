
// We need to build a static spa, @see https://kit.svelte.dev/docs/single-page-apps
//import adapter from '@sveltejs/adapter-auto';
import adapter from '@sveltejs/adapter-static';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	// Consult https://kit.svelte.dev/docs/integrations#preprocessors
	// for more information about preprocessors
	preprocess: vitePreprocess(),

	kit: {
		adapter: adapter({
			pages: '../src/main/resources/static',
			fallback: 'index.html' // Fallback-Option für alle dynamischen Routen (SPA-Modus)
		}),
		
		paths: {
			relative: false,
		},
		prerender: {
			// Da du eine Single-Page Application baust, schalte die strikte Prerender-Prüfung ab.
			entries: [] // Leere entries sorgt dafür, dass keine Routen explizit vorgerendert werden müssen
		}
	}
};

export default config;