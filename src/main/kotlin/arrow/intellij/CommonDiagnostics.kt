package arrow.intellij

import org.jetbrains.kotlin.analysis.api.KaSession

/**
 * Per-element diagnostics across the Analysis API changes between 261 and 263:
 *
 * - 261/262: `KaSession.diagnostics(KtElement, KaDiagnosticCheckerFilter)`, used by [LegacyDiagnostics].
 * - 263+:    that member is gone; replaced by the `KaDiagnostics` query (`KtElement.diagnostics().directOnly(true)`),
 *            used by `Diagnostics263` from the `ide263` source set (compiled against 263 but packaged in the same jar).
 */
internal val commonDiagnostics: DiagnosticsCompat by lazy {
    val kaDiagnosticsPresent = try {
        KaSession::class.java.classLoader.loadClass("org.jetbrains.kotlin.analysis.api.diagnostics.KaDiagnostics")
        true
    } catch (_: ClassNotFoundException) {
        false
    }
    when {
        kaDiagnosticsPresent -> Diagnostics263
        else -> LegacyDiagnostics
    }
}
