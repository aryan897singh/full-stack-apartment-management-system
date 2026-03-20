/**
 * A singleton-like utility to fetch and cache application settings.
 * This avoids re-fetching from the /configuration endpoint on every page load.
 */

// We use a Promise to ensure we only fetch once, even if getAppSettings()
// is called multiple times before the first fetch completes.
let settingsPromise = null;

async function fetchAndCacheSettings() {
    // 1. Check if settings are already in sessionStorage
    const cachedSettings = sessionStorage.getItem('appSettings');
    if (cachedSettings) {
        // console.log('Returning settings from cache.');
        return JSON.parse(cachedSettings);
    }

    // 2. If not, fetch from the server
    // console.log('Fetching settings from server...');
    try {
        const response = await fetch('/configuration');
        if (!response.ok) {
            throw new Error('Failed to fetch configuration');
        }
        const settingsList = await response.json(); // This is the array

        // 3. Transform the array into a simple key-value object
        const settingsMap = {};
        for (const setting of settingsList) {
            settingsMap[setting.configKey] = setting.configValue;
        }

        // 4. Save to sessionStorage
        sessionStorage.setItem('appSettings', JSON.stringify(settingsMap));
        return settingsMap;

    } catch (error) {
        console.error("CRITICAL: Could not load app settings.", error);
        // Return an empty object so the app doesn't crash,
        // but this indicates a major problem.
        return {};
    }
}

/**
 * Public function to get application settings.
 * All other scripts should call this.
 * * @returns {Promise<Object>} A promise that resolves to the settings map.
 * * @example
 * const settings = await getAppSettings();
 * const currency = settings.CURRENCY_SYMBOL;
 * document.getElementById('nav-brand').textContent = settings.APARTMENT_NAME;
 */
function getAppSettings() {
    if (!settingsPromise) {
        settingsPromise = fetchAndCacheSettings();
    }
    return settingsPromise;
}