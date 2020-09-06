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

export function ranking() {
    return [
        {
            word: '上海交大',
            type: '新',
            change: 1
        },
        {
            word: '软件学院',
            type: '热',
            change: -1
        },
        {
            word: '迟先生',
            type: '',
            change: 2
        },
        {
            word: '苏大佬',
            type: '荐',
            change: 0
        }
    ];
}

export const users = [
    {
        username: 'lycheenut',
        password: 'admin',
        userType: 1
    },
    {
        username: 'HR_SU',
        password: 'qwerty',
        userType: 1
    }
];

export const entries = [
    {
        title: 'AirCon',
        summary: 'Summary text of AirCon',
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
    }
];
