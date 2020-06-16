package application.prir.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

@Log4j2
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/calculations")
public class TaskComputationController {

    //ilosc zadan do wykonania
    private static final int computationTaskNumber = 500;

    static {
        Stack<ComputationTask> taskGeneration = new Stack<>();
        for (int i = 0; i < computationTaskNumber; i++) {
            taskGeneration.push(new ComputationTask(computationTaskNumber - i, computationTaskNumber));
        }
        allTasks = taskGeneration;
        tasksInProgress = new Stack<>();
        finishedTasks = new Stack<>();
    }

    private static Stack<ComputationTask> allTasks;
    private static Stack<ComputationTask> tasksInProgress;
    private static Stack<ComputationTask> finishedTasks;

    @PostConstruct
    private void scheduleCleaning() {
        returnUnfinishedTaskOnStack();
    }

    @GetMapping
    public ComputationTask getComputationTask() {
        log.info("Ilosc zadan do wykonania: {}", allTasks.size());
        log.info("Ilosc ukonczonych zadan: {}", finishedTasks.size());
        if (!allTasks.empty()) {
            ComputationTask task = allTasks.pop();
            task.setStartTime(LocalDateTime.now());
            tasksInProgress.push(task);
            log.info("Ilosc zadan w trakcie wykonywania: {}", tasksInProgress.size());
            log.info("Nastepuje wyslanie zadania o ID = {}, czas wyslania zadania: {}", task.getId(), task.getStartTime());
            return task;
        } else {
            return null;
        }
    }

    @PostMapping
    public void getComputationResult(@RequestBody ComputationTask computationResult) {
        if (computationResult != null) {
            log.info("Odebrano wyniki dla zadania o id {}", computationResult.getId());
            tasksInProgress.remove(computationResult);
            finishedTasks.push(computationResult);
        }
    }

    private void returnUnfinishedTaskOnStack() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                moveUnfinishedTaskToExecutionStack();
            }
        }, 0, 1000);
    }

    private void moveUnfinishedTaskToExecutionStack() {
        List<ComputationTask> unfinishedTasks = new ArrayList<>();
        for (ComputationTask taskInProgress : tasksInProgress) {
            if (checkIfTaskComputationTakeToLong(taskInProgress)) {
                allTasks.push(taskInProgress);
                unfinishedTasks.add(taskInProgress);
                log.info("Zadanie o ID {} zostało przywrócone na stos zadań do wykonania", taskInProgress.getId());
            }
        }
        unfinishedTasks.forEach(it -> tasksInProgress.remove(it));
    }
    private boolean checkIfTaskComputationTakeToLong(ComputationTask task) {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - task.getStartTime().toEpochSecond(ZoneOffset.UTC) > 5;
    }
}
