# Design System: Shri Shyam Kaleshwar Residency — Property Management Panel

## 1. Overview
A dark-mode-first property/estate management admin panel. Built with Tailwind CSS (CDN, forms + container-queries plugins), Material Symbols Outlined icons, and Google Fonts (Manrope + Inter). All screens share a fixed left sidebar, a fixed top header, and a scrollable main content canvas.

**Mode:** Dark theme is default (`<html class="dark">`). Design for dark mode as primary; do not design light-mode variants unless explicitly requested.

---

## 2. Color System

Use these exact hex values as Tailwind theme color tokens (Material Design 3-style naming). Never use raw Tailwind palette colors (e.g. `blue-500`, `gray-800`) — only these tokens.

### 2.1 Core surface colors
| Token | Hex | Usage |
|---|---|---|
| `background` | `#0b1326` | Page background |
| `surface` | `#0b1326` | Base surface, matches background |
| `surface-dim` | `#0b1326` | Dim surface variant |
| `surface-bright` | `#31394d` | Brightest surface variant |
| `surface-container-lowest` | `#060e20` | Deepest inset containers (search bars, pagination bar) |
| `surface-container-low` | `#131b2e` | Card/table/panel backgrounds |
| `surface-container` | `#171f33` | Sidebar background, glass panel base |
| `surface-container-high` | `#222a3d` | Active nav item bg, secondary buttons |
| `surface-container-highest` | `#2d3449` | Hover states, elevated chips |

### 2.2 Text colors
| Token | Hex | Usage |
|---|---|---|
| `on-background` | `#dae2fd` | Default body text on background |
| `on-surface` | `#dae2fd` | Primary text on surface |
| `on-surface-variant` | `#c2c6d6` | Secondary/muted text, labels |
| `inverse-on-surface` | `#283044` | Text on inverse surfaces |
| `inverse-surface` | `#dae2fd` | Inverse surface fill |

### 2.3 Primary (blue) — brand/action color
| Token | Hex | Usage |
|---|---|---|
| `primary` | `#adc6ff` | Primary buttons, active nav, icons, links |
| `primary-fixed` | `#d8e2ff` | Fixed light primary |
| `primary-fixed-dim` | `#adc6ff` | Fixed dim primary |
| `primary-container` | `#4d8eff` | Primary container fill |
| `on-primary` | `#002e6a` | Text/icons on primary bg |
| `on-primary-container` | `#00285d` | Text on primary-container |
| `on-primary-fixed` | `#001a42` | Text on primary-fixed |
| `on-primary-fixed-variant` | `#004395` | Secondary text on primary-fixed |
| `inverse-primary` | `#005ac2` | Primary color on inverse surfaces |
| `surface-tint` | `#adc6ff` | Elevation tint overlay |

### 2.4 Secondary (green/teal) — success/positive status
| Token | Hex | Usage |
|---|---|---|
| `secondary` | `#4edea3` | "Occupied" status badges, success accents, notification dot |
| `secondary-fixed` | `#6ffbbe` | Fixed light secondary |
| `secondary-fixed-dim` | `#4edea3` | Fixed dim secondary |
| `secondary-container` | `#00a572` | Secondary container fill |
| `on-secondary` | `#003824` | Text on secondary bg |
| `on-secondary-container` | `#00311f` | Text on secondary-container |
| `on-secondary-fixed` | `#002113` | Text on secondary-fixed |
| `on-secondary-fixed-variant` | `#005236` | Secondary text on secondary-fixed |

### 2.5 Tertiary (orange) — highlight/warning accent
| Token | Hex | Usage |
|---|---|---|
| `tertiary` | `#ffb786` | Tertiary accents |
| `tertiary-fixed` | `#ffdcc6` | Fixed light tertiary |
| `tertiary-fixed-dim` | `#ffb786` | Fixed dim tertiary |
| `tertiary-container` | `#df7412` | "New Property"/CTA highlight container |
| `on-tertiary` | `#502400` | Text on tertiary |
| `on-tertiary-container` | `#461f00` | Text on tertiary-container |
| `on-tertiary-fixed` | `#311400` | Text on tertiary-fixed |
| `on-tertiary-fixed-variant` | `#723600` | Secondary text on tertiary-fixed |

### 2.6 Error (red) — destructive/overdue status
| Token | Hex | Usage |
|---|---|---|
| `error` | `#ffb4ab` | "Overdue" status text, destructive actions |
| `error-container` | `#93000a` | Error container fill |
| `on-error` | `#690005` | Text on error bg |
| `on-error-container` | `#ffdad6` | Text on error-container |

### 2.7 Outline
| Token | Hex | Usage |
|---|---|---|
| `outline` | `#8c909f` | Default borders |
| `outline-variant` | `#424754` | Subtle dividers (always used with low opacity, e.g. `/5`, `/10`, `/20`) |

**Rule for Stitch:** Borders almost never appear at full opacity. Standard pattern: `border border-outline-variant/5` or `/10` for hairline dividers; `/20` for emphasized borders (e.g. status badges, primary-tinted buttons).

---

## 3. Typography

### 3.1 Font families
- **Headline font:** `Manrope` (weights 400, 600, 700, 800) — used for all headings, logo text, nav labels, buttons.
- **Body font:** `Inter` (weights 300, 400, 500, 600) — used for body copy, table data, inputs.
- Load via Google Fonts: `Manrope:wght@400;600;700;800` and `Inter:wght@300;400;500;600`.
- Tailwind config maps: `font-headline` → Manrope, `font-body` → Inter, `font-label` → Inter.
- Base `<body>` font-family defaults to Inter.
- Apply Manrope explicitly to `h1, h2, h3` and any element with class `.headline`.

### 3.2 Type scale
| Element | Classes | Notes |
|---|---|---|
| Page title (H1) | `text-5xl font-extrabold tracking-tighter text-on-surface leading-tight` | Manrope |
| Eyebrow/kicker label | `font-label uppercase text-[11px] tracking-[0.2em] text-secondary font-bold` | Above H1 |
| Sidebar brand name | `text-lg font-bold text-primary tracking-tighter leading-tight uppercase` | |
| Sidebar sub-label | `text-[10px] font-manrope tracking-widest text-slate-500 mt-1 uppercase` | |
| Nav item text | `font-manrope tracking-tight font-semibold` (text-base default) | |
| Table column header | `text-[10px] font-bold uppercase tracking-[0.15em] text-on-surface-variant` | |
| Table primary cell text | `text-sm font-bold text-on-surface` | |
| Table secondary/meta text | `text-[10px] text-on-surface-variant` | |
| Button text | `font-bold text-sm` or `font-bold font-manrope tracking-tight` | |
| Badge/status text | `text-[10px] font-bold uppercase tracking-wider` | |
| Footer brand | `font-headline font-bold text-sm tracking-widest` | |
| Footer copyright | `text-[10px] text-slate-600 uppercase tracking-widest` | |

**Rule for Stitch:** Small uppercase text with wide letter-spacing (`tracking-widest`, `tracking-[0.15em]`, `tracking-[0.2em]`) is a recurring signature style for labels, eyebrows, badges, and footers. Always pair uppercase + letter-spacing + small size (10–11px) for metadata/label text.

---

## 4. Layout Structure

Every interior page follows this exact three-region layout:

```
┌─────────────────────────────────────────────────┐
│ Sidebar (fixed, w-64, full height, left: 0)      │  Header (fixed, left-64 → right-0, h-16)
│                                                   │──────────────────────────────────────────
│  - Brand block                                   │  Main content (ml-64, pt-16)
│  - Nav links (5 items)                           │  ├─ padding: p-10, max-w-7xl mx-auto
│  - Bottom CTA button                             │  ├─ Page header (title + actions)
│                                                   │  ├─ Tab navigation
│                                                   │  ├─ Filter/toolbar bar
│                                                   │  ├─ Data table / content card
│                                                   │  └─ Footer
└─────────────────────────────────────────────────┘
```

### 4.1 Sidebar (`<aside>`)
- Fixed position: `fixed left-0 top-0 bottom-0 z-40`
- Size: `w-64`, full height (`h-screen`)
- Layout: `flex flex-col`, padding `p-6`
- Background: `bg-slate-900/80 dark:bg-surface-container backdrop-blur-3xl`
- Shadow: `shadow-2xl shadow-blue-900/20`
- **Brand block** (`mb-10`): Uppercase bold brand name in `primary` color, plus a smaller uppercase tracking-widest sub-label in muted slate below it.
- **Nav** (`flex-1 space-y-2`): Each link is `flex items-center px-4 py-3` with a Material Symbol icon (`mr-3`) and label.
    - Default state: `text-slate-400 hover:text-slate-200 transition-colors group`
    - **Active state:** `text-primary bg-surface-container-high rounded-lg font-bold group scale-[0.98] active:duration-75` — this is the pattern for whichever page is current.
- **Bottom Actions** (`mt-auto pt-6 border-t border-white/5 flex flex-col gap-3`):
    - **Create New Lease (Primary CTA):** Full-width button, `bg-primary text-on-primary py-3 rounded-xl font-bold font-manrope tracking-tight flex items-center justify-center gap-2`, icon `add_card`, hover adds glow shadow `hover:shadow-[0_0_20px_rgba(173,198,255,0.3)] transition-all`. Link points to `/owner-pages/create-lease.html`.
    - **Add New Property (Secondary CTA):** Full-width button, `bg-surface-container-high text-on-surface hover:bg-surface-container-highest py-3 rounded-xl font-bold font-manrope tracking-tight flex items-center justify-center gap-2 border border-outline-variant/10 transition-all`, icon `add_home`. Link points to `/owner-pages/add-new-flat.html`.
### 4.2 Header (`<header>`)
- Fixed: `fixed top-0 right-0 left-64 z-30`
- Height: `h-16`, padding `px-8`
- Background: `backdrop-blur-2xl bg-slate-950/80 dark:bg-surface/80 border-b border-white/5`
- Layout: `flex items-center justify-between`
- Left side: search input, `flex-1` — pill-shaped, `w-96`, `bg-surface-container-lowest rounded-full pl-10 pr-4 py-1.5 text-xs`, search icon absolutely positioned left, `focus:ring-1 ring-[#3b82f6]`.
- Right side (`flex items-center gap-6`): icon buttons (notifications with a small `secondary`-colored dot badge, help), a vertical divider (`h-8 w-[1px] bg-white/5 mx-2`), then a small uppercase tracking-widest label (e.g. role/context, e.g. "Admin Panel").

### 4.3 Main content (`<main>`)
- Offsets: `ml-64 pt-16 min-h-screen`
- Inner wrapper: `p-10 max-w-7xl mx-auto`

### 4.4 Page header block
- `flex flex-col md:flex-row md:items-end justify-between mb-10 gap-6`
- Left: eyebrow label + H1 title.
- Right: `flex gap-4` action buttons — typically one secondary button (`bg-surface-container-high hover:bg-surface-container-highest`, icon + label) and one primary button (`bg-primary text-on-primary`, icon + label, with soft shadow `shadow-[0_4px_20px_rgba(173,198,255,0.2)]`).

### 4.5 Tab navigation
- Pill-shaped container: `flex items-center gap-1 bg-surface-container-low p-1 rounded-xl w-fit mb-8 border border-outline-variant/5`
- Each tab: `px-8 py-2.5 rounded-lg text-sm font-bold transition-all`
    - Active: `bg-primary text-on-primary shadow-lg`
    - Inactive: `text-on-surface-variant hover:text-on-surface hover:bg-surface-container-highest`

### 4.6 Filter/toolbar bar
- `bg-surface-container-low rounded-2xl p-4 mb-8 flex items-center justify-between gap-4 border border-outline-variant/5`
- Left: section label (`text-sm font-bold text-on-surface`)
- Right: muted italic count text + a sort/filter icon at `opacity-50`

### 4.7 Data table card
- Container: `bg-surface-container-low rounded-3xl overflow-hidden border border-outline-variant/5`
- `<table class="w-full text-left border-collapse">`
- `<thead>` row: `border-b border-outline-variant/10`, header cells `px-8 py-5` with the column-header type style (§3.2).
- `<tbody>`: `divide-y divide-outline-variant/5`
- Row: `hover:bg-surface-container-highest transition-colors group`
- Cell padding: `px-8 py-6`
- **Primary entity cell pattern:** icon/number chip (`w-10 h-10 bg-primary/10 rounded-xl flex items-center justify-center text-primary font-bold`) + stacked text (bold primary line + muted small line below).
- **Status badge pattern:** `px-3 py-1 rounded-full border` with color-matched background/text/border at low opacity, e.g. success: `bg-secondary/10 text-secondary border-secondary/20`; use `bg-error/10 text-error border-error/20` for negative states.
- **Date/value + tag pattern:** bold value line, then a small bold/medium colored sub-label underneath (color reflects status: `text-secondary` for good, `text-error` for overdue, `text-on-surface-variant` for neutral).
- **Row actions cell:** `flex items-center justify-center gap-3` — an icon-only button (`p-2 hover:bg-surface-container-lowest rounded-lg text-on-surface-variant`) plus a labeled pill button (`px-4 py-1.5 bg-surface-container-highest text-primary text-xs font-bold rounded-lg border border-primary/20 hover:bg-primary hover:text-on-primary transition-all`).
- **Pagination footer:** `px-8 py-4 bg-surface-container-lowest/50 flex items-center justify-between` — left: uppercase muted "Page X of Y" text; right: two small square icon buttons (`p-2 bg-surface-container-high rounded-lg`, disabled state `opacity-30`).

### 4.8 Footer
- `mt-20 pb-10 border-t border-white/5 pt-10 text-center`
- Small primary dot + bold tracking-widest brand name, centered (`flex items-center justify-center gap-2 mb-4`)
- Credit line: muted text with the developer name in `on-surface` color
- Copyright line: smallest, `slate-600`, uppercase, tracking-widest

---

## 5. Component Patterns (reusable rules)

### 5.1 Buttons
| Type | Classes |
|---|---|
| Primary | `bg-primary text-on-primary px-6 py-3 rounded-xl font-bold text-sm flex items-center gap-2 shadow-[0_4px_20px_rgba(173,198,255,0.2)]` |
| Secondary | `bg-surface-container-high hover:bg-surface-container-highest text-on-surface px-6 py-3 rounded-xl font-bold text-sm border border-outline-variant/10` |
| Full-width sidebar CTA | `bg-primary text-on-primary py-3 rounded-xl font-bold w-full` + hover glow |
| Icon-only | `p-2 hover:bg-surface-container-lowest rounded-lg text-on-surface-variant` |
| Small pill action | `px-4 py-1.5 bg-surface-container-highest text-primary text-xs font-bold rounded-lg border border-primary/20 hover:bg-primary hover:text-on-primary` |

### 5.2 Status badges (semantic colors)
- Positive/active/occupied → `secondary` family
- Warning/attention → `tertiary` family
- Negative/overdue/error → `error` family
- Neutral/informational → `on-surface-variant` / `outline`
- Pattern: `bg-{color}/10 text-{color} border border-{color}/20 rounded-full px-3 py-1 text-[10px] font-bold uppercase tracking-wider`

### 5.3 Cards/containers
- Radius scale: `rounded-lg` (0.75rem) for small components, `rounded-xl` (1rem) for buttons/inputs, `rounded-2xl`/`rounded-3xl` for cards and table containers, `rounded-full` for pills/badges/search bars.
- Default custom radius values: `DEFAULT: 0.5rem`, `lg: 0.75rem`, `xl: 1rem`, `full: 9999px`.
- Elevated glass panel style (for modals/overlays): `.glass-panel { background: rgba(23,31,51,0.8); backdrop-filter: blur(24px); }`

### 5.4 Icons
- Use **Material Symbols Outlined** exclusively (not filled, not other icon sets).
- Global weight setting: `font-variation-settings: 'FILL' 0, 'wght' 300, 'GRAD' 0, 'opsz' 24;`
- Icon sizing via text-size utility classes: `text-sm`, `text-lg`, default (24px base).

### 5.5 Inputs
- Search/text inputs: pill-shaped, `bg-surface-container-lowest`, no visible border by default, `focus:ring-1` with an accent ring color, placeholder in muted slate.

---

## 6. Spacing & Sizing Conventions
- Sidebar width: `w-64` (16rem) — always reserve `ml-64` / `left-64` on adjacent fixed/main elements.
- Header height: `h-16` — always pair with `pt-16` on main content.
- Page content max width: `max-w-7xl`, centered with `mx-auto`, outer padding `p-10`.
- Card/section vertical rhythm: `mb-8` to `mb-10` between major blocks.
- Table cell padding: `px-8 py-6` (body), `px-8 py-5` (header).
- Icon-to-label gap in buttons/nav: `gap-2` to `gap-3` (or `mr-3` for nav icons).

---

## 7. Design Principles for Stitch to Preserve
1. **Dark, glassy, low-saturation base** with **high-saturation accent colors** (blue primary, green secondary, orange tertiary, red error) used sparingly and only for meaningful signals (status, actions, active states).
2. **Opacity-layered borders/fills** everywhere instead of solid contrasting borders — always use `/5`, `/10`, `/20` opacity modifiers on `outline-variant` and semantic colors rather than solid borders.
3. **Two-font hierarchy**: Manrope for anything structural/branded (headings, nav, buttons), Inter for data/body/inputs.
4. **Uppercase + wide tracking + tiny size** for all metadata, labels, eyebrows, and footer text — this is the app's signature "label" typographic treatment.
5. **Consistent fixed shell** (sidebar + header) across every page; only the main content region changes.
6. **Status-driven color coding** in tables: green = good/occupied/renewing, red = overdue/error, neutral gray = standard/no action needed.
7. **Rounded, soft geometry** throughout — no sharp corners; radius increases with element size (buttons < cards < table containers < pills).
8. **Icon chip pattern** for primary entities in lists (colored translucent square/rounded box containing a bold label, e.g. unit number).

---

## 8. Instructions for Stitch Generation
When generating new screens for this app:
- Reuse the sidebar and header exactly as specified in §4.1–4.2 on every page; only change the active nav state.
- Always wrap page content in the `p-10 max-w-7xl mx-auto` container inside `<main class="ml-64 pt-16 min-h-screen">`.
- Any new data table must follow the exact column-header, row, badge, and pagination patterns in §4.7.
- Any new status/tag must map to one of the four semantic colors (primary, secondary, tertiary, error) — do not invent new colors.
- All new buttons must match one of the five button patterns in §5.1 — do not create new button styles.
- Maintain the eyebrow-label + H1 pattern (§3.2, §4.4) at the top of every page.
- Keep the same footer (§4.8) at the bottom of every page.