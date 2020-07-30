export function contents(param) {
    return {
        title: param,
        summary: 'Summary text of ' + param,
        catalog: [
            {
                label: 'History',
                text: 'text...',
                children: [
                    {
                        label: 'Ancient History',
                        text: 'text...'
                    },
                    {
                        label: 'Modern History',
                        text: 'text...',
                    }
                ]
            },
            {
                label: 'Features',
                text: 'text...',
                children: [
                    {
                        label: 'Advantages',
                        text: 'text...',
                        children: [
                            {
                                label: 'Good water-absorbing quality',
                                text: 'text...'
                            },
                            {
                                label: 'Good air-permability',
                                text: 'text...'
                            }
                        ]
                    },
                    {
                        label: 'Disadvantages',
                        text: 'text...'
                    }
                ]
            },
            {
                label: 'Principles',
                text: 'text...'
            }
        ]
    };
}
