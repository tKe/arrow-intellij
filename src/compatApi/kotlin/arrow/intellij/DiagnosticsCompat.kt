package arrow.intellij

import org.jetbrains.kotlin.analysis.api.KaExperimentalApi
import org.jetbrains.kotlin.analysis.api.KaSession
import org.jetbrains.kotlin.analysis.api.components.KaDiagnosticCheckerFilter
import org.jetbrains.kotlin.analysis.api.diagnostics.KaDiagnosticWithPsi
import org.jetbrains.kotlin.psi.KtElement

/**
 * Per-element diagnostics, whose Analysis API differs between IDE generations (see [commonDiagnostics]).
 * Lives in its own source set (compiled against the oldest supported platform) so both the 261/262 and the 263
 * implementations can depend on it without depending on each other.
 */
internal interface DiagnosticsCompat {
    fun commonDiagnosticsFor(session: KaSession, element: KtElement): Collection<KaDiagnosticWithPsi<*>>
}

@OptIn(KaExperimentalApi::class)
internal object LegacyDiagnostics : DiagnosticsCompat {
    override fun commonDiagnosticsFor(session: KaSession, element: KtElement): Collection<KaDiagnosticWithPsi<*>> =
        with(session) { element.diagnostics(KaDiagnosticCheckerFilter.ONLY_COMMON_CHECKERS) }
}
