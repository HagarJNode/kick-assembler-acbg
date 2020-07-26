package de.achimonline.kickassembler.acbg.runconfiguration;

import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataContext;
import org.jetbrains.annotations.NotNull;

public class KickAssemblerCommandLineState extends CommandLineState {
    private KickAssemblerRunConfiguration kickAssemblerRunConfiguration;
    private DataContext dataContext;

    protected KickAssemblerCommandLineState(KickAssemblerRunConfiguration kickAssemblerRunConfiguration, ExecutionEnvironment executionEnvironment) {
        super(executionEnvironment);

        this.kickAssemblerRunConfiguration = kickAssemblerRunConfiguration;
        this.dataContext = DataContext.EMPTY_CONTEXT;

        DataManager.getInstance().getDataContextFromFocusAsync().then(it -> dataContext = it);
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        return null; // TODO
    }

    @NotNull
    @Override
    public ExecutionResult execute(@NotNull Executor executor, @NotNull ProgramRunner runner) throws ExecutionException {
        ConsoleView console = createConsole(executor);
        GeneralCommandLine commandLine = KickAssemblerCommandLine.generate(kickAssemblerRunConfiguration, dataContext);
        ProcessHandler processHandler = new KillableColoredProcessHandler(commandLine);

        if (console != null) {
            console.attachToProcess(processHandler);
        }

        AnAction[] actions = createActions(console, processHandler, executor);

        return new DefaultExecutionResult(console, processHandler, actions);
    }
}
