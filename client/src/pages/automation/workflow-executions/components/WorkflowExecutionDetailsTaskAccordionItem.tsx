import {AccordionContent, AccordionItem, AccordionTrigger} from '@radix-ui/react-accordion';
import {CheckCircledIcon, CrossCircledIcon} from '@radix-ui/react-icons';
import {TaskExecutionModel} from 'middleware/helios/execution';
import InlineSVG from 'react-inlinesvg';
import ReactJson from 'react-json-view';

const WorkflowExecutionDetailsTaskAccordionItem = ({taskExecutions}: {taskExecutions: TaskExecutionModel[]}) => {
    return (
        <>
            {taskExecutions.map((taskExecution) => {
                const {endDate, id, input, output, startDate, workflowTask} = taskExecution;

                const duration = startDate && endDate && Math.round(endDate?.getTime() - startDate.getTime());

                return (
                    workflowTask?.label && (
                        <AccordionItem key={id} value={id || ''}>
                            <AccordionTrigger className="flex w-full items-center justify-between border-gray-100 bg-white px-2 py-3 data-[state=closed]:border-b">
                                <div className="flex items-center text-sm">
                                    {taskExecution?.component?.icon && (
                                        <InlineSVG className="mr-1 h-6 w-6" src={taskExecution?.component?.icon} />
                                    )}

                                    {workflowTask?.name || workflowTask?.type}
                                </div>

                                <div className="flex items-center">
                                    <span className="ml-auto mr-2 text-xs">{duration}ms</span>

                                    {taskExecution.status === 'COMPLETED' && (
                                        <CheckCircledIcon className="h-5 w-5 text-green-500" />
                                    )}

                                    {taskExecution.status === 'FAILED' && (
                                        <CrossCircledIcon className="h-5 w-5 text-red-500" />
                                    )}
                                </div>
                            </AccordionTrigger>

                            <AccordionContent className="space-y-4 border-b border-gray-100 p-3">
                                <div className="rounded-lg">
                                    <header className="flex items-center justify-between rounded-md bg-gray-100 px-2 py-1">
                                        <span className="text-sm font-medium uppercase">Input</span>

                                        <span className="text-xs">{startDate?.toLocaleString()}</span>
                                    </header>

                                    {input && typeof input === 'object' ? (
                                        <ReactJson enableClipboard={false} src={input as object} />
                                    ) : (
                                        <pre className="mt-2 text-xs">{input}</pre>
                                    )}
                                </div>

                                <div className="rounded-lg">
                                    <header className="flex items-center justify-between rounded-md bg-gray-100 px-2 py-1">
                                        <span className="text-sm font-medium uppercase">Output</span>

                                        <span className="text-xs">{endDate?.toLocaleString()}</span>
                                    </header>

                                    <pre className="mt-2 text-sm">
                                        {output && typeof output === 'object' ? (
                                            <ReactJson enableClipboard={false} src={output as object} />
                                        ) : (
                                            <pre className="mt-2 text-xs">{output || 'No output data.'}</pre>
                                        )}
                                    </pre>
                                </div>
                            </AccordionContent>
                        </AccordionItem>
                    )
                );
            })}
        </>
    );
};

export default WorkflowExecutionDetailsTaskAccordionItem;
